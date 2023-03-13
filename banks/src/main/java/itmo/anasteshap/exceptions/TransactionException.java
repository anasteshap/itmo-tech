package itmo.anasteshap.exceptions;

import itmo.anasteshap.models.Amount;

import java.util.UUID;

public class TransactionException extends RuntimeException {
    private TransactionException(String message) {
        super(message);
    }

    public static TransactionException failedTransaction(String message) {
        return new TransactionException("transaction failed: " + message);
    }

    public static TransactionException transactionAlreadyExists(UUID id) {
        return new TransactionException("transaction with id: " + id + " already exists");
    }

    public static TransactionException transactionDoesNotExist(UUID id) {
        return new TransactionException("transaction with id: " + id + " doesn't exist");
    }

    public static TransactionException sumExceedingLimit(Amount sum, Amount limit) {
        return new TransactionException("sum " + sum.getValue() + " exceeding the limit " + limit.getValue());
    }
}
