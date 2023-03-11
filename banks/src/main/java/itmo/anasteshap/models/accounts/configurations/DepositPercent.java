package itmo.anasteshap.models.accounts.configurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.Percent;

import java.math.BigDecimal;

/**
 * Deposit percent with borders of sum
 */
@Getter
@AllArgsConstructor
public class DepositPercent {
    @NonNull
    private final Percent percent;
    @NonNull
    private final Amount leftBorder;
    @NonNull
    private final Amount rightBorder;

    public DepositPercent(Percent percent, Amount leftBorder) {
        this.percent = percent;
        this.leftBorder = leftBorder;
        this.rightBorder = new Amount(BigDecimal.valueOf(100000000));
    }
}
