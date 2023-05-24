package itmo.anasteshap.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCatRequest {
    Long id;
    String name;
    LocalDate birthDate;
    String breed;
    String color;
    Integer tailLength;
    Long ownerId;
}
