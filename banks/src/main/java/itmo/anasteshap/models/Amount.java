package itmo.anasteshap.models;

import lombok.Getter;
import itmo.anasteshap.exceptions.TransactionException;

import java.math.BigDecimal;

/**
 * Wrapper for BigDecimal with a check for non-negativity or the value
 */
public class Amount {
    @Getter
    private final BigDecimal value;

    public Amount(BigDecimal amount) throws RuntimeException {
        if (amount.signum() < 0)
            throw new RuntimeException();

        this.value = amount;
    }
}