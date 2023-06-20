package itmo.anasteshap.dto.create;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateOwnerRequest {
    private String name;
    private LocalDate birthDate;
}
