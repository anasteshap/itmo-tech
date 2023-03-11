package itmo.anasteshap.models.accounts.configurations;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Configuration of deposit account
 */
public class DepositAccountConfiguration {
    @NonNull private List<DepositPercent> percents;

    public DepositAccountConfiguration(List<DepositPercent> percents) {
        validateDepositPercents(percents);
        this.percents = percents;
    }

    public List<DepositPercent> getPercents() {
        return Collections.unmodifiableList(percents);
    }

    public void setPercents(List<DepositPercent> percents) {
        validateDepositPercents(percents);
        this.percents = percents;
    }

    private void validateDepositPercents(@NonNull List<DepositPercent> percents) {
        if (percents.isEmpty()) {
            throw new RuntimeException();
        }

        if (!Objects.equals(percents.get(0).getLeftBorder().getValue(), BigDecimal.ZERO)) {
            throw new RuntimeException();
        }

        for (var percent : percents) {
            var left = percent.getLeftBorder().getValue();
            var right = percent.getRightBorder().getValue();
            if (left.compareTo(right) >= 0) { // если правая граница <= левой
                throw new RuntimeException();
            }
        }

        for (int i = 0; i < percents.size() - 1; i++) {
            var rightBorder = percents.get(i).getRightBorder().getValue();
            var leftBorder = percents.get(i + 1).getLeftBorder().getValue();
            if (!Objects.equals(leftBorder, rightBorder)) {
                throw new RuntimeException();
            }

            var leftPercent = percents.get(i).getPercent().value();
            var rightPercent = percents.get(i + 1).getPercent().value();
            if (leftPercent.compareTo(rightPercent) >= 0) {
                throw new RuntimeException();
            }
        }
    }
}
