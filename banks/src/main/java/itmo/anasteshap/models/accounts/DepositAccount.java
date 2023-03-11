package itmo.anasteshap.models.accounts;

import itmo.anasteshap.entities.Client;
import itmo.anasteshap.models.Amount;
import itmo.anasteshap.models.accounts.configurations.BankConfiguration;
import itmo.anasteshap.models.accounts.configurations.DepositAccountConfiguration;
import itmo.anasteshap.models.dateTimeProvider.Clock;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;
import java.util.*;

public class DepositAccount extends Account {
    private final DepositAccountConfiguration configuration;
    private final Amount limitForDubiousClient;
    private final Calendar endOfPeriod;
    private final Clock clock;
    private Amount percentageAmount = new Amount(BigDecimal.ZERO);

    public DepositAccount(@NonNull Clock clock, Client client, @NonNull BankConfiguration bankConfiguration, @NonNull Period periodInDays) {
        super(client, BankAccountTypes.Deposit);
        this.clock = clock;
        this.clock.addAction(this::update);

        this.configuration = bankConfiguration.getDepositAccount();
        this.limitForDubiousClient = bankConfiguration.getLimitForDubiousClient();
        this.balance = new Amount(BigDecimal.ZERO);

        this.endOfPeriod = clock.currentTime();
        this.endOfPeriod.add(Calendar.DAY_OF_YEAR, periodInDays.getDays());
    }

    @Override
    public void decreaseAmount(@NonNull Amount sum) {
        if (clock.currentTime().get(Calendar.DAY_OF_YEAR) <= endOfPeriod.get(Calendar.DAY_OF_YEAR))
            throw new RuntimeException();

        if (client.isDubious()) {
            if (sum.getValue().compareTo(limitForDubiousClient.getValue()) > 0) {
                throw new RuntimeException();
            }
        }

        if (balance.getValue().compareTo(sum.getValue()) < 0) {
            throw new RuntimeException();
        }

        balance = new Amount(balance.getValue().subtract(sum.getValue()));
    }

    @Override
    public void update() {
        if (clock.currentTime().get(Calendar.DAY_OF_YEAR) > endOfPeriod.get(Calendar.DAY_OF_YEAR)) {
            throw new RuntimeException();
        }

        var percent = configuration.getPercents().stream()
                .filter(x -> (x.getLeftBorder().getValue().compareTo(balance.getValue()) <= 0 && x.getLeftBorder().getValue().compareTo(balance.getValue()) > 0))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        int daysInYear = clock.currentTime().getActualMaximum(Calendar.DAY_OF_YEAR);
        percentageAmount = new Amount(balance.getValue().multiply(percent.getPercent().value()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(daysInYear), 10, RoundingMode.HALF_UP).add(this.percentageAmount.getValue()));

        if (clock.currentTime().get(Calendar.DAY_OF_YEAR) == endOfPeriod.get(Calendar.DAY_OF_YEAR)) {
            balance = new Amount(balance.getValue().add(percentageAmount.getValue()));
            percentageAmount = new Amount(BigDecimal.ZERO);
        }
    }
}
