package itmo.anasteshap.models.accounts;

import itmo.anasteshap.entities.Client;
import itmo.anasteshap.models.dateTimeProvider.Clock;
import lombok.NonNull;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.accounts.configurations.BankConfiguration;
import itmo.anasteshap.models.accounts.configurations.DebitAccountConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class DebitAccount extends Account {
    private final DebitAccountConfiguration configuration;
    private final Amount limitForDubiousClient;
    private final Clock clock;
    private Amount percentageAmount = new Amount(BigDecimal.ZERO);
    private int countOfDays;

    public DebitAccount(Clock clock, Client client, @NonNull BankConfiguration bankConfiguration) {
        super(client, BankAccountTypes.Debit);
        this.clock = clock;
        this.clock.addAction(this::update);
        this.countOfDays = this.clock.currentTime().getActualMaximum(Calendar.DAY_OF_MONTH);

        this.configuration = bankConfiguration.getDebitAccount();
        this.limitForDubiousClient = bankConfiguration.getLimitForDubiousClient();
        this.balance = new Amount(BigDecimal.ZERO);
    }

    @Override
    public void decreaseAmount(@NonNull Amount sum) {
        if (balance.getValue().compareTo(sum.getValue()) < 0)
            throw new RuntimeException();

        if (client.isDubious()) {
            if (sum.getValue().compareTo(limitForDubiousClient.getValue()) > 0)
                throw new RuntimeException();
        }

        balance = new Amount(balance.getValue().subtract(sum.getValue()));
    }

    @Override
    public void update() {
        int daysInYear = clock.currentTime().getActualMaximum(Calendar.DAY_OF_YEAR);

        percentageAmount = new Amount(balance.getValue()
                .multiply(configuration.getPercent().value())
                .divide(BigDecimal.valueOf(daysInYear), 10, RoundingMode.HALF_UP)
                .add(percentageAmount.getValue()));

        countOfDays--;
        if (countOfDays != 0)
            return;

        balance = new Amount(balance.getValue()
                .add(percentageAmount.getValue()));

        percentageAmount = new Amount(BigDecimal.ZERO);
        countOfDays = clock.currentTime().getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
