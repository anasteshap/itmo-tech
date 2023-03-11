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
public class CreditAccountConfiguration {
    @NonNull private Amount commission;
    @NonNull private Amount limit;
}
