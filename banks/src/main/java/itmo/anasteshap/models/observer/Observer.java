package itmo.anasteshap.models.observer;

import lombok.NonNull;

public interface Observer<T> {
    void update(@NonNull T data);
}
