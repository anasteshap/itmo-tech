package itmo.anasteshap.models.accounts.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import itmo.anasteshap.models.Percent;

/**
 * Configuration of debit account
 */
@Data
@AllArgsConstructor
public class DebitAccountConfiguration {
    @NonNull private Percent percent;
}
