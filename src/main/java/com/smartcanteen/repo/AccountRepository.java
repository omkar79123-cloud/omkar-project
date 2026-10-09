package com.smartcanteen.repo;

import com.smartcanteen.model.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import javax.persistence.LockModeType;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where a.username = :username")
    Optional<Account> findByUsernameForUpdate(@Param("username") String username);
}
