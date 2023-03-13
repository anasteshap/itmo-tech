package itmo.anasteshap.models.accounts.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import itmo.anasteshap.models.Amount;

/**
 * Configuration of credit account
 */
@Data
@AllArgsConstructor
public class CreditAccountConfiguration implements Cloneable {
    @NonNull private Amount commission;
    @NonNull private Amount limit;

    @Override
    public Object clone() throws CloneNotSupportedException {
            return super.clone();
    }
}
