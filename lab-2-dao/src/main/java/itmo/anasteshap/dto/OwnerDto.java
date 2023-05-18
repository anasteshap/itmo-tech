package itmo.anasteshap.dto;

import java.time.LocalDate;

public record OwnerDto (Long id, String name, LocalDate birthDate) {
    // нужен ли список котов?
}
