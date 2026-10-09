package com.smartcanteen.repo;

import com.smartcanteen.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.Lock;
import javax.persistence.LockModeType;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contract test guarding the pessimistic lock used by checkout. The database
 * integration suite should run against MySQL in CI; this test fails fast if a
 * future refactor removes the lock and could re-introduce wallet overspending.
 */
class CheckoutConcurrencyContractTest {
    @Test void checkoutAccountLookupUsesWriteLock() throws Exception {
        Method method = AccountRepository.class.getMethod("findByUsernameForUpdate", String.class);
        assertEquals(LockModeType.PESSIMISTIC_WRITE, method.getAnnotation(Lock.class).value());
    }
}
