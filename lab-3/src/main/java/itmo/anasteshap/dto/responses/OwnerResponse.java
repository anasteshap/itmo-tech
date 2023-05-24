package itmo.anasteshap.dto.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OwnerResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
}
