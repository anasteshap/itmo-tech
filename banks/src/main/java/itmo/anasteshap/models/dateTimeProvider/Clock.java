package itmo.anasteshap.models.dateTimeProvider;

import java.util.Calendar;

public interface Clock {
    Calendar currentTime();
    void addAction(Runnable action);
}
