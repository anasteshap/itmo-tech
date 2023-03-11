package itmo.anasteshap.exceptions;

import lombok.NonNull;

public class ClientException extends RuntimeException {
    @NonNull public static RuntimeException InvalidAddress() {
        return new RuntimeException();
    }
}
