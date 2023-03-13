package itmo.anasteshap.exceptions;

public class BankException extends RuntimeException {
    private BankException(String message) {
        super(message);
    }

    public static BankException bankAlreadyExists(String name) {
        return new BankException("bank with name: " + name + " already exists");
    }

    public static BankException bankDoesNotExist(String name) {
        return new BankException("bank with name: " + name + " doesn't exist");
    }
}
