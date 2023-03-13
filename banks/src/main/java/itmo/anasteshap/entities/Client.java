package itmo.anasteshap.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import joptsimple.internal.Strings;
import lombok.*;
import itmo.anasteshap.models.observer.Observer;

@Builder
@EqualsAndHashCode
public class Client implements Observer<String> {
    private final List<String> notifications = new ArrayList<>();
    @Getter
    private final UUID id = UUID.randomUUID();
    @Getter
    private final String name;
    @Getter
    private final String surname;
    @Getter
    private String address;
    @Getter
    private String passport;

    public Client(@NonNull String name, @NonNull String surname, String address, String passport) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
    }

    /**
     * Check client - is dubious or not
     *
     * @return true if client is dubious
     */
    public boolean isDubious() {
        return Strings.isNullOrEmpty(this.address) || Strings.isNullOrEmpty(this.passport);
    }

    public List<String> getNotifications() {
        return Collections.unmodifiableList(this.notifications);
    }

    /**
     * Add address if client haven't it
     *
     * @param address new address
     */
    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    /**
     * Add passport if client haven't it
     *
     * @param passport new passport
     */
    public void setPassport(@NonNull String passport) {
        this.passport = passport;
    }

    /**
     * Add notification about changes in client's bank account
     *
     * @param data notification message
     */
    @Override
    public void update(@NonNull String data) {
        this.notifications.add(data);
    }
}
