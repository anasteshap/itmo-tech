package itmo.anasteshap.models.accounts.commands;

import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.accounts.Account;

/**
 * Command of incoming sum to account
 */
public class Income implements BalanceOperationCommand {
    private final Account account;
    private final Amount sum;

    public Income(@NonNull Account account, @NonNull Amount sum) {
        this.account = account;
        this.sum = sum;
    }

    @Override
    public void cancel() {
        account.decreaseAmount(this.sum);
    }

    @Override
    public void execute() {
        account.increaseAmount(this.sum);
    }
}
