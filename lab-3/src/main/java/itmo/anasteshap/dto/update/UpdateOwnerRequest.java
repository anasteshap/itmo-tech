package itmo.anasteshap.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateOwnerRequest {
    Long id;
    private String name;
    private LocalDate birthDate;
}
