package itmo.anasteshap.models.dateTimeProvider;

import lombok.NonNull;

import java.util.*;

/**
 * Clock for rewinding date and using it as current time
 */
public class RewindClock implements Clock {
    private int periodInDays = 0;
    private final List<Runnable> rewinds = new ArrayList<>();

    /**
     * Get current time with rewinding
     *
     * @return current time with rewinding
     */
    @Override
    public Calendar currentTime() {
        var date = GregorianCalendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, periodInDays);
        return date;
    }

    /**
     * Rewind time
     *
     * @param periodInDays count of days for rewinding time
     */
    public void rewindTime(int periodInDays) {
        if (periodInDays < 0)
            throw new RuntimeException();

        for (int i = 0; i < periodInDays; i++) {
            this.periodInDays++;
            rewinds.forEach(Runnable::run);
        }
    }

    /**
     * Add action that is performed when rewinding time for one day
     *
     * @param action action for perform
     */
    @Override
    public void addAction(@NonNull Runnable action) {
        rewinds.add(action);
    }
}
