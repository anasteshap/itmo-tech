package itmo.anasteshap.dto;

import java.time.LocalDate;

public record CatDto(Long id, String name, LocalDate birthDate, String breed, String color, OwnerDto owner) {
}
