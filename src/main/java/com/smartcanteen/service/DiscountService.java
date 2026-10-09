package com.smartcanteen.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DiscountService {
    private static final BigDecimal SIX_HUNDRED = new BigDecimal("600.00");
    private static final BigDecimal TWELVE_HUNDRED = new BigDecimal("1200.00");
    private static final BigDecimal SIXTEEN_HUNDRED = new BigDecimal("1600.00");

    public BigDecimal tierRate(BigDecimal subtotal) {
        if (subtotal.compareTo(SIX_HUNDRED) < 0) return BigDecimal.ZERO;
        if (subtotal.compareTo(TWELVE_HUNDRED) < 0) return new BigDecimal("0.05");
        if (subtotal.compareTo(SIXTEEN_HUNDRED) <= 0) return new BigDecimal("0.10");
        return new BigDecimal("0.15");
    }
    public BigDecimal tierDiscount(BigDecimal subtotal) {
        return money(subtotal.multiply(tierRate(subtotal)));
    }
    public BigDecimal couponDiscount(BigDecimal subtotal, String coupon) {
        if (coupon != null && "SAVE50".equalsIgnoreCase(coupon.trim())
                && subtotal.compareTo(new BigDecimal("500.00")) >= 0) return new BigDecimal("50.00");
        return BigDecimal.ZERO.setScale(2);
    }
    public BigDecimal total(BigDecimal subtotal, String coupon) {
        BigDecimal result = subtotal.subtract(tierDiscount(subtotal)).subtract(couponDiscount(subtotal, coupon));
        return money(result.max(BigDecimal.ZERO));
    }
    public BigDecimal money(BigDecimal value) { return value.setScale(2, RoundingMode.HALF_UP); }
}
