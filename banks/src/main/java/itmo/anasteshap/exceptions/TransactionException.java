package itmo.anasteshap.exceptions;

import lombok.NonNull;

public class TransactionException extends RuntimeException {
    @NonNull public static RuntimeException NegativeAmount() {
        return new RuntimeException();
    }
}
