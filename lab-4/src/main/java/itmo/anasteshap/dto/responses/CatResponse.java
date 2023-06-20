package itmo.anasteshap.dto.responses;

import itmo.anasteshap.entities.models.Breed;
import itmo.anasteshap.entities.models.Color;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CatResponse {
    Long id;
    String name;
    LocalDate birthDate;
    Breed breed;
    Color color;
    Integer tailLength;
    OwnerResponse owner;
}
