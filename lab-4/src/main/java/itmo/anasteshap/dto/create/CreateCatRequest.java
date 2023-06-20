package itmo.anasteshap.dto.create;

import itmo.anasteshap.entities.models.Breed;
import itmo.anasteshap.entities.models.Color;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCatRequest {
    String name;
    LocalDate birthDate;
    Breed breed;
    Color color;
    Integer tailLength;
    Long ownerId;
}
