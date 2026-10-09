package com.smartcanteen.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name="account_id") private Account account;
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true) private java.util.List<OrderItem> items = new java.util.ArrayList<>();
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal subtotal;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal tierDiscount;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal couponDiscount;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal total;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private PaymentMethod paymentMethod;
    @Column(nullable = false) private Instant createdAt;
    @Column(nullable = false) private boolean refunded;
    protected Order() {}
    public Order(Account account, BigDecimal subtotal, BigDecimal tierDiscount, BigDecimal couponDiscount,
                 BigDecimal total, PaymentMethod paymentMethod) {
        this.account = account; this.subtotal = subtotal; this.tierDiscount = tierDiscount;
        this.couponDiscount = couponDiscount; this.total = total; this.paymentMethod = paymentMethod;
        this.createdAt = Instant.now();
    }
    public Long getId() { return id; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTierDiscount() { return tierDiscount; }
    public BigDecimal getCouponDiscount() { return couponDiscount; }
    public BigDecimal getTotal() { return total; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public BigDecimal getRemainingWalletBalance() { return account.getWalletBalance(); }
    public void addItem(OrderItem item) { items.add(item); }
    public java.util.List<OrderItem> getItems() { return items; }
    public Instant getCreatedAt() { return createdAt; }
    public String getAccountUsername() { return account.getUsername(); }
    public boolean isRefunded() { return refunded; }
    public void refund() { refunded = true; }
}
