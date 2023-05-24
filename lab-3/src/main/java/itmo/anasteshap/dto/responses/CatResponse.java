package itmo.anasteshap.dto.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CatResponse {
    Long id;
    String name;
    LocalDate birthDate;
    String breed;
    String color;
    Integer tailLength;
    OwnerResponse owner;
}
