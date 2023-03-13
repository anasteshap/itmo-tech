package itmo.anasteshap.exceptions;

import java.util.UUID;

public class ClientException extends RuntimeException {
    private ClientException(String message) {
        super(message);
    }

    public static ClientException clientAlreadyExists(UUID id) {
        return new ClientException("client with id: " + id + " already exists");
    }

    public static ClientException clientDoesNotExist(UUID id) {
        return new ClientException("client with id: " + id + " doesn't exist");
    }
}
