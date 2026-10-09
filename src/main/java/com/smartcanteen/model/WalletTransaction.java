package com.smartcanteen.model;
import javax.persistence.*; import java.math.BigDecimal; import java.time.Instant;
@Entity @Table(name="wallet_transactions")
public class WalletTransaction {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="account_id") private Account account;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id") private Order order;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal amount;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Type type;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal balanceAfter; @Column(nullable=false) private Instant createdAt;
 public enum Type { DEBIT,CREDIT }
 protected WalletTransaction() {}
 public WalletTransaction(Account a,Order o,BigDecimal amount,Type t){account=a;order=o;this.amount=amount;type=t;balanceAfter=a.getWalletBalance();createdAt=Instant.now();}
 public Long getId(){return id;} public BigDecimal getAmount(){return amount;} public Type getType(){return type;} public BigDecimal getBalanceAfter(){return balanceAfter;} public Instant getCreatedAt(){return createdAt;}
}
