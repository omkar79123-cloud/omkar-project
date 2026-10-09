package com.smartcanteen.web;

import com.smartcanteen.model.Order;
import com.smartcanteen.service.OrderService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(Authentication auth, @Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(service.checkout(auth.getName(), request)));
    }
    public static class OrderResponse {
        public final Long orderId;
        public final BigDecimal subtotal, tierDiscount, couponDiscount, total, remainingWalletBalance;
        public final String paymentMethod;
        OrderResponse(Order o) {
            orderId=o.getId(); subtotal=o.getSubtotal(); tierDiscount=o.getTierDiscount();
            couponDiscount=o.getCouponDiscount(); total=o.getTotal(); paymentMethod=o.getPaymentMethod().name();
            remainingWalletBalance=o.getRemainingWalletBalance();
        }
    }
}
