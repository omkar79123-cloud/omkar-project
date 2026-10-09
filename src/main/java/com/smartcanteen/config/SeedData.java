package com.smartcanteen.config;

import com.smartcanteen.model.Account;
import com.smartcanteen.model.Role;
import com.smartcanteen.repo.AccountRepository;
import com.smartcanteen.repo.ProductRepository;
import com.smartcanteen.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Configuration
public class SeedData {
    @Bean CommandLineRunner seed(AccountRepository accounts, ProductRepository products, PasswordEncoder encoder,
                                 org.springframework.core.env.Environment env) {
        return args -> {
            String studentPassword = env.getProperty("app.seed.student-password");
            if (hasValue(studentPassword) && !accounts.findByUsername("student1").isPresent())
                accounts.save(new Account("student1", "Student One", encoder.encode(studentPassword), Role.STUDENT, new BigDecimal("2000.00")));
            String facultyPassword = env.getProperty("app.seed.faculty-password");
            if (hasValue(facultyPassword) && !accounts.findByUsername("faculty1").isPresent())
                accounts.save(new Account("faculty1", "Faculty One", encoder.encode(facultyPassword), Role.FACULTY, new BigDecimal("3000.00")));
            String adminPassword = env.getProperty("app.seed.admin-password");
            if (hasValue(adminPassword) && !accounts.findByUsername("admin").isPresent())
                accounts.save(new Account("admin", "Canteen Admin", encoder.encode(adminPassword), Role.ADMIN, BigDecimal.ZERO));
            seedProduct(products, "Veg Thali", "180.00"); seedProduct(products, "Sandwich", "120.00");
            seedProduct(products, "Fruit Bowl", "90.00"); seedProduct(products, "Coffee", "60.00"); seedProduct(products, "Premium Meal", "650.00");
        };
    }
    private boolean hasValue(String value) { return value != null && !value.trim().isEmpty(); }
    private void seedProduct(ProductRepository r, String n, String p) { if (!r.findByName(n).isPresent()) r.save(new Product(n,new BigDecimal(p))); }
}
