package com.smartcanteen.repo;
import com.smartcanteen.model.Product; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ProductRepository extends JpaRepository<Product,Long> { List<Product> findByActiveTrueOrderByName(); Optional<Product> findByName(String name); }
