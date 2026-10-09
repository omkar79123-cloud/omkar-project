package com.smartcanteen.service;

import com.smartcanteen.model.*;
import com.smartcanteen.repo.*;
import com.smartcanteen.web.ApiException;
import com.smartcanteen.web.CheckoutRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class OrderService {
    private final AccountRepository accounts;
    private final OrderRepository orders;
    private final DiscountService discounts;
    private final ProductRepository products;
    private final WalletTransactionRepository transactions;

    public OrderService(AccountRepository accounts, OrderRepository orders, DiscountService discounts, ProductRepository products, WalletTransactionRepository transactions) {
        this.accounts = accounts; this.orders = orders; this.discounts = discounts; this.products=products; this.transactions=transactions;
    }

    @Transactional
    public Order checkout(String username, CheckoutRequest request) {
        Account account = accounts.findByUsernameForUpdate(username)
                .orElseThrow(() -> new ApiException(404, "Account not found"));
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CheckoutRequest.Item item : request.getItems()) {
            Product product=products.findById(item.getProductId()).filter(Product::isActive).orElseThrow(() -> new ApiException(400,"Product not found"));
            if (item.getQuantity() <= 0) throw new ApiException(400, "Quantity must be positive");
            subtotal = subtotal.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        subtotal = discounts.money(subtotal);
        BigDecimal tier = discounts.tierDiscount(subtotal);
        BigDecimal coupon = discounts.couponDiscount(subtotal, request.getCoupon());
        BigDecimal total = discounts.total(subtotal, request.getCoupon());
        Order order = new Order(account, subtotal, tier, coupon, total, request.getPaymentMethod());
        for (CheckoutRequest.Item item : request.getItems()) {
            Product p=products.findById(item.getProductId()).get();
            order.addItem(new OrderItem(order,p,item.getQuantity()));
        }
        if (request.getPaymentMethod() == PaymentMethod.WALLET) {
            if (account.getWalletBalance().compareTo(total) < 0)
                throw new ApiException(409, "Insufficient wallet balance");
            account.debit(total);
            Order saved=orders.save(order); transactions.save(new WalletTransaction(account,saved,total,WalletTransaction.Type.DEBIT)); return saved;
        }
        return orders.save(order);
    }
}
