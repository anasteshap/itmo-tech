package itmo.anasteshap.dto.update;

import lombok.Data;

@Data
public class UpdateFleaRequest {
    Long id;
    String name;
    Long catId;
}
