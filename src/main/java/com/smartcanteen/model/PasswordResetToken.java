package com.smartcanteen.model;
import javax.persistence.*; import java.time.Instant;
@Entity @Table(name="password_reset_tokens")
public class PasswordResetToken {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=128) private String token;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="account_id") private Account account;
 @Column(nullable=false) private Instant expiresAt; @Column(nullable=false) private boolean used;
 protected PasswordResetToken(){}
 public PasswordResetToken(String t,Account a,Instant e){token=t;account=a;expiresAt=e;}
 public boolean valid(){return !used && expiresAt.isAfter(Instant.now());}
 public void use(){used=true;} public Account getAccount(){return account;}
}
