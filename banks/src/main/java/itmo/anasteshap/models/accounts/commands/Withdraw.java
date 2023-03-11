package itmo.anasteshap.models.accounts.commands;

import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.accounts.Account;

/**
 * Command of withdrawing sum from account
 */
public class Withdraw implements BalanceOperationCommand {
    private final Account account;
    private final Amount sum;

    public Withdraw(@NonNull Account account, @NonNull Amount sum) {
        this.account = account;
        this.sum = sum;
    }

    @Override
    public void cancel() {
        account.increaseAmount(sum);
    }

    @Override
    public void execute() {
        account.decreaseAmount(sum);
    }
}
