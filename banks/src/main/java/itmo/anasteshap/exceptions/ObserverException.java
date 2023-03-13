package itmo.anasteshap.exceptions;

public class ObserverException extends RuntimeException {
    private ObserverException(String message) {
        super(message);
    }

    public static ObserverException subscriberAlreadyExists() {
        return new ObserverException("Subscriber already exists");
    }

    public static ObserverException subscriberDoesNotExist() {
        return new ObserverException("subscriber doesn't exist");
    }
}
