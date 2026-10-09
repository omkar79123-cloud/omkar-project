package com.smartcanteen.repo;
import com.smartcanteen.model.WalletTransaction; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> { List<WalletTransaction> findByAccountUsernameOrderByCreatedAtDesc(String username); }
