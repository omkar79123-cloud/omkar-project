package com.smartcanteen.web;

import com.smartcanteen.model.PaymentMethod;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class CheckoutRequest {
    @NotEmpty @Valid private List<Item> items;
    private String coupon;
    @NotNull private PaymentMethod paymentMethod;
    public List<Item> getItems() { return items; }
    public String getCoupon() { return coupon; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public static class Item {
        @NotNull private Long productId;
        @Min(1) private int quantity;
        public Long getProductId() { return productId; }
        public int getQuantity() { return quantity; }
    }
}
