package itmo.anasteshap.models.transaction;

import itmo.anasteshap.models.accounts.commands.BalanceOperationCommand;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

/**
 * Bank transaction
 */
public class Transaction {
    private final BalanceOperationCommand command;
    @Getter
    private final UUID id = UUID.randomUUID();
    @Getter
    private TransactionStates transactionState;
    @Getter
    private String statusMessage;

    public Transaction(@NonNull BalanceOperationCommand command) {
        this.command = command;
        this.transactionState = TransactionStates.Started;
        this.statusMessage = "BankTransaction " + this.transactionState;
    }

    /**
     * Executing the transaction
     */
    public void doTransaction() {
        if (transactionState != TransactionStates.Started)
            throw new RuntimeException();

        try {
            command.execute();
            transactionState = TransactionStates.Ended;
            statusMessage = "BankTransaction " + this.transactionState;
        } catch (RuntimeException e) {
            transactionState = TransactionStates.Failed;
            statusMessage = "BankTransaction " + transactionState + ": " + e.getMessage();
        }
    }

    /**
     * Canceling the transaction
     */
    public void undo() {
        if (transactionState != TransactionStates.Ended)
            throw new RuntimeException();

        try {
            command.cancel();
            transactionState = TransactionStates.Canceled;
            statusMessage = "BankTransaction " + this.transactionState;
        } catch (RuntimeException e) {
            transactionState = TransactionStates.Failed;
            statusMessage = "BankTransaction " + this.transactionState + ": " + e.getMessage();
        }
    }
}