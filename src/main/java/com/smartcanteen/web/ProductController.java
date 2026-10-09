package com.smartcanteen.web;
import com.smartcanteen.model.Product; import com.smartcanteen.repo.ProductRepository; import org.springframework.web.bind.annotation.*; import javax.validation.Valid; import javax.validation.constraints.*; import java.math.BigDecimal; import java.util.*;
@RestController @RequestMapping("/api/products")
public class ProductController { private final ProductRepository products; public ProductController(ProductRepository p){products=p;} @GetMapping public List<Product> list(){return products.findByActiveTrueOrderByName();}
 @PostMapping @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')") public Product create(@Valid @RequestBody ProductRequest r){return products.save(new Product(r.name,r.price));}
 @PutMapping("/{id}") @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')") public Product update(@PathVariable Long id,@Valid @RequestBody ProductRequest r){Product p=products.findById(id).orElseThrow(()->new ApiException(404,"Product not found"));p.update(r.name,r.price,r.active);return products.save(p);}
 @DeleteMapping("/{id}") @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')") public void deactivate(@PathVariable Long id){Product p=products.findById(id).orElseThrow(()->new ApiException(404,"Product not found"));p.update(p.getName(),p.getPrice(),false);products.save(p);}
 public static class ProductRequest{@NotBlank @Size(max=200) public String name; @NotNull @DecimalMin("0.01") public BigDecimal price; public boolean active=true;}
}
