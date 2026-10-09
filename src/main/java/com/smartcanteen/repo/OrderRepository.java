package com.smartcanteen.repo;
import com.smartcanteen.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import javax.persistence.LockModeType;
public interface OrderRepository extends JpaRepository<Order, Long> {
 java.util.List<Order> findByAccountUsernameOrderByCreatedAtDesc(String username);
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select o from Order o where o.id = :id")
 java.util.Optional<Order> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") Long id);
}
