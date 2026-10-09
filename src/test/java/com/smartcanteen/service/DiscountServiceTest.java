package com.smartcanteen.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {
    private final DiscountService service = new DiscountService();
    @Test void appliesExactTierBoundaries() {
        assertEquals("0.00", service.tierDiscount(new BigDecimal("599.99")).toString());
        assertEquals("30.00", service.tierDiscount(new BigDecimal("600.00")).toString());
        assertEquals("120.00", service.tierDiscount(new BigDecimal("1200.00")).toString());
        assertEquals("160.00", service.tierDiscount(new BigDecimal("1600.00")).toString());
        assertEquals("240.15", service.tierDiscount(new BigDecimal("1601.00")).toString());
    }
}
