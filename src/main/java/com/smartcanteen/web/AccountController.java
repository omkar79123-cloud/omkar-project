package com.smartcanteen.web;

import com.smartcanteen.model.Account;
import com.smartcanteen.repo.AccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRepository accounts;
    public AccountController(AccountRepository accounts) { this.accounts = accounts; }
    @GetMapping("/me")
    public AccountResponse me(Authentication auth) {
        Account a = accounts.findByUsername(auth.getName()).orElseThrow(() -> new ApiException(404, "Account not found"));
        return new AccountResponse(a.getUsername(), a.getDisplayName(), a.getRole().name(), a.getWalletBalance());
    }
    public static class AccountResponse {
        public final String username, displayName, role;
        public final BigDecimal walletBalance;
        public AccountResponse(String u, String d, String r, BigDecimal b) { username=u; displayName=d; role=r; walletBalance=b; }
    }
}
