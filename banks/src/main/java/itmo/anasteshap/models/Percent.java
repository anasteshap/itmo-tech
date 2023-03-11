package itmo.anasteshap.models;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Percent in fractional form
 */
public record Percent(@Getter BigDecimal value) {
    public Percent(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException();

        this.value = value.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
    }
}