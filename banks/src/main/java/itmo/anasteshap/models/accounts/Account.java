package itmo.anasteshap.models.accounts;

import itmo.anasteshap.entities.Client;
import itmo.anasteshap.exceptions.TransactionException;
import lombok.Getter;
import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.transaction.Transaction;

import java.util.*;

/**
 * Abstract bank account
 */
public abstract class Account {
    protected final List<Transaction> transactions = new ArrayList<>();
    @Getter
    protected final BankAccountTypes type;
    @Getter
    protected final UUID id;
    @Getter
    protected final Client client;
    @Getter
    protected Amount balance;

    protected Account(@NonNull Client client, @NonNull BankAccountTypes type) {
        this.type = type;
        this.id = UUID.randomUUID();
        this.client = client;
    }

    public abstract Object getConfiguration() throws CloneNotSupportedException;

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Get transaction by id
     *
     * @param transactionId id of transaction
     * @return transaction
     */
    public Transaction getTransaction(@NonNull UUID transactionId) {
        return transactions.stream()
                .filter(x -> x.getId().equals(transactionId))
                .findFirst()
                .orElseThrow(() -> TransactionException.transactionDoesNotExist(transactionId));
    }

    /**
     * Increase sum
     *
     * @param sum sum for increasing
     */
    public void increaseAmount(@NonNull Amount sum) {
        balance = new Amount(balance.getValue().add(sum.getValue()));
    }

    /**
     * Decrease sum
     *
     * @param sum sum for decreasing
     */
    public abstract void decreaseAmount(@NonNull Amount sum);

    /**
     * Save transaction in account
     *
     * @param transaction transaction for saving
     */
    public void saveChanges(@NonNull Transaction transaction) {
        if (transactions.contains(transaction)) {
            throw TransactionException.transactionAlreadyExists(transaction.getId());
        }
        transactions.add(transaction);
    }

    /**
     * Update sum of commission
     */
    public abstract void update();
}