package itmo.anasteshap.exceptions;

import java.util.UUID;

public class AccountException extends RuntimeException {
    private AccountException(String message) {
        super(message);
    }

    public static AccountException invalidConfiguration(String message) {
        return new AccountException(message);
    }

    public static AccountException notEnoughMoney() {
        return new AccountException("don't have enough money on account");
    }

    public static AccountException invalidPeriodOfAccount() {
        return new AccountException("period of the account is invalid");
    }

    public static AccountException expiration(UUID id) {
        return new AccountException("account with id: " + id + " expired");
    }

    public static AccountException accountAlreadyExists(UUID id) {
        return new AccountException("account with id: " + id + " already exists");
    }

    public static AccountException accountDoesNotExist(UUID id) {
        return new AccountException("account with id: " + id + " doesn't exist");
    }
}
