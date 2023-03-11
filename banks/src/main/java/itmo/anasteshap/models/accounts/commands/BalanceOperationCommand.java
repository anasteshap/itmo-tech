package itmo.anasteshap.models.accounts.commands;

public interface BalanceOperationCommand extends Command {
    /**
     * Cancel balance operation command
     */
    void cancel();
}
