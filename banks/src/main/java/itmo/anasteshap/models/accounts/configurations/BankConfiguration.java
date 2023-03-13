package itmo.anasteshap.models.accounts.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import itmo.anasteshap.models.Amount;

/**
 * Common bank configuration of all bank accounts types
 */
@Data
@AllArgsConstructor
public class BankConfiguration implements Cloneable{
    @NonNull
    private CreditAccountConfiguration creditAccount;
    @NonNull
    private DebitAccountConfiguration debitAccount;
    @NonNull
    private DepositAccountConfiguration depositAccount;
    @NonNull
    private Amount limitForDubiousClient;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
