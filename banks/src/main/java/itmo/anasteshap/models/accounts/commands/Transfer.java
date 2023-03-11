package itmo.anasteshap.models.accounts.commands;

import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.accounts.Account;

/**
 * Command of transfer sum from one account to another
 */
public class Transfer implements BalanceOperationCommand {
    private final Account toAccount;
    private final Account fromAccount;
    private final Amount sum;

    public Transfer(@NonNull Account toAccount, @NonNull Account fromAccount, @NonNull Amount sum) {
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.sum = sum;
    }

    @Override
    public void cancel() {
        try {
            fromAccount.decreaseAmount(sum);
        } catch (Exception e) {
            fromAccount.increaseAmount(sum);
            throw new RuntimeException();
        }

        toAccount.increaseAmount(sum);
    }

    @Override
    public void execute() {
        try {
            toAccount.decreaseAmount(sum);
        } catch (Exception e) {
            toAccount.increaseAmount(sum);
            throw new RuntimeException();
        }

        fromAccount.increaseAmount(sum);
    }
}
