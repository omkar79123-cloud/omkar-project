package com.smartcanteen.config;

import com.smartcanteen.repo.AccountRepository;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean UserDetailsService userDetailsService(AccountRepository accounts) {
        return username -> accounts.findByUsername(username)
                .map(a -> User.withUsername(a.getUsername()).password(a.getPasswordHash())
                        .roles(a.getRole().name()).build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
    @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests(a -> a.antMatchers("/", "/index.html", "/css/**", "/js/**", "/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/products").permitAll()
                .anyRequest().authenticated()).httpBasic();
        return http.build();
    }
}
