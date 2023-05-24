package itmo.anasteshap.dto.create;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCatRequest {
    String name;
    LocalDate birthDate;
    String breed;
    String color;
    Integer tailLength;
    Long ownerId;
}
