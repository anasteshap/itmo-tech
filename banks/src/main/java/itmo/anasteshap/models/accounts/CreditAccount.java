package itmo.anasteshap.models.accounts;

import itmo.anasteshap.exceptions.AccountException;
import itmo.anasteshap.exceptions.TransactionException;
import lombok.NonNull;
import itmo.anasteshap.entities.Client;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.accounts.configurations.BankConfiguration;
import itmo.anasteshap.models.accounts.configurations.CreditAccountConfiguration;

public class CreditAccount extends Account {
    private final CreditAccountConfiguration configuration;
    private final Amount limitForDubiousClient;

    public CreditAccount(Client client, @NonNull BankConfiguration bankConfiguration) {
        super(client, BankAccountTypes.Credit);
        this.configuration = bankConfiguration.getCreditAccount();
        this.limitForDubiousClient = bankConfiguration.getLimitForDubiousClient();
        this.balance = bankConfiguration.getCreditAccount().getLimit();
    }

    @Override
    public Object getConfiguration() throws CloneNotSupportedException {
        return configuration.clone();
    }

    @Override
    public void decreaseAmount(@NonNull Amount sum) {
        if (client.isDubious()) {
            if (sum.getValue().compareTo(limitForDubiousClient.getValue()) > 0)
                throw TransactionException.sumExceedingLimit(sum, limitForDubiousClient);
        }

        if (sum.getValue().compareTo(balance.getValue()) > 0) {
            var num = balance.getValue().subtract(sum.getValue()).subtract(configuration.getCommission().getValue());
            if (num.abs().compareTo(configuration.getLimit().getValue()) < 0)
                throw AccountException.notEnoughMoney();
        }

        balance = (sum.getValue().compareTo(balance.getValue()) <= 0)
                ? new Amount(sum.getValue())
                : new Amount(balance.getValue().subtract(sum.getValue().add(configuration.getCommission().getValue())));
    }

    @Override
    public void update() {
    }
}
