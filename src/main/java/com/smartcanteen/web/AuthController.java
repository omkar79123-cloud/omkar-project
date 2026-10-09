package com.smartcanteen.web;
import com.smartcanteen.model.*; import com.smartcanteen.repo.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import javax.validation.Valid; import javax.validation.constraints.*; import java.math.BigDecimal; import java.security.SecureRandom; import java.time.*; import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AccountRepository accounts; private final PasswordResetTokenRepository tokens; private final PasswordEncoder encoder; private final SecureRandom random=new SecureRandom();
 public AuthController(AccountRepository a,PasswordResetTokenRepository t,PasswordEncoder e){accounts=a;tokens=t;encoder=e;}
 @PostMapping("/register") public Map<String,String> register(@Valid @RequestBody Registration r){if(accounts.findByUsername(r.username).isPresent())throw new ApiException(409,"Username already exists"); accounts.save(new Account(r.username,r.displayName,encoder.encode(r.password),Role.STUDENT,new BigDecimal("2000.00"))); return Collections.singletonMap("message","Registration successful");}
 @PostMapping("/password-reset/request") public Map<String,String> request(@Valid @RequestBody ResetRequest r){Account a=accounts.findByUsername(r.username).orElse(null); if(a!=null){byte[] b=new byte[32];random.nextBytes(b);String token=Base64.getUrlEncoder().withoutPadding().encodeToString(b);tokens.save(new PasswordResetToken(token,a,Instant.now().plus(Duration.ofMinutes(30))));} return Collections.singletonMap("message","If the account exists, reset instructions were sent");}
 @PostMapping("/password-reset/confirm") public Map<String,String> confirm(@Valid @RequestBody ConfirmReset r){PasswordResetToken t=tokens.findByToken(r.token).filter(PasswordResetToken::valid).orElseThrow(()->new ApiException(400,"Invalid or expired reset token"));t.getAccount().changePassword(encoder.encode(r.password));t.use();accounts.save(t.getAccount());tokens.save(t);return Collections.singletonMap("message","Password reset successful");}
 public static class Registration { @NotBlank @Size(max=100) public String username; @NotBlank @Size(max=200) public String displayName; @NotBlank @Size(min=8,max=200) public String password; }
 public static class ResetRequest { @NotBlank public String username; }
 public static class ConfirmReset { @NotBlank public String token; @NotBlank @Size(min=8,max=200) public String password; }
}
