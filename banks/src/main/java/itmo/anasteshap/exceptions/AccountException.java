package itmo.anasteshap.exceptions;

import lombok.NonNull;

public class AccountException extends RuntimeException {
    @NonNull public static RuntimeException InvalidConfiguration() {
        return new RuntimeException();
    }
}
