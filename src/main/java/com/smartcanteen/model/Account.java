package com.smartcanteen.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String username;
    @Column(nullable = false) private String displayName;
    @Column(nullable = false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Role role;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal walletBalance;
    protected Account() {}
    public Account(String username, String displayName, String passwordHash, Role role, BigDecimal balance) {
        this.username = username; this.displayName = displayName; this.passwordHash = passwordHash;
        this.role = role; this.walletBalance = balance;
    }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public BigDecimal getWalletBalance() { return walletBalance; }
    public void debit(BigDecimal amount) { walletBalance = walletBalance.subtract(amount); }
    public void credit(BigDecimal amount) { walletBalance = walletBalance.add(amount); }
    public void changePassword(String hash) { passwordHash = hash; }
}
