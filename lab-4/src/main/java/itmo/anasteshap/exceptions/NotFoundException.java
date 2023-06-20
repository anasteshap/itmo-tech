package itmo.anasteshap.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Item not found");
    }
}
