package itmo.anasteshap.models.accounts.configurations;

import itmo.anasteshap.exceptions.AccountException;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Configuration of deposit account
 */
public class DepositAccountConfiguration implements Cloneable{
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
            throw AccountException.invalidConfiguration("no deposit interest");
        }

        if (!Objects.equals(percents.get(0).getLeftBorder().getValue(), BigDecimal.ZERO)) {
            throw AccountException.invalidConfiguration("leftBorder of first depositPercent must be equal to 0");
        }

        for (var percent : percents) {
            var left = percent.getLeftBorder().getValue();
            var right = percent.getRightBorder().getValue();
            if (left.compareTo(right) >= 0) { // если правая граница <= левой
                throw AccountException.invalidConfiguration("leftBorder of depositPercent must be more or equal than rightBorder of this percent");
            }
        }

        for (int i = 0; i < percents.size() - 1; i++) {
            var rightBorder = percents.get(i).getRightBorder().getValue();
            var leftBorder = percents.get(i + 1).getLeftBorder().getValue();
            if (!Objects.equals(leftBorder, rightBorder)) {
                throw AccountException.invalidConfiguration("leftBorder of first depositPercent and rightBorder of next one must be equal");
            }

            var leftPercent = percents.get(i).getPercent().value();
            var rightPercent = percents.get(i + 1).getPercent().value();
            if (leftPercent.compareTo(rightPercent) >= 0) {
                throw AccountException.invalidConfiguration("last depositPercent must be <= next depositPercent");
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
