package itmo.anasteshap.services;

import itmo.anasteshap.dto.create.CreateFleaRequest;
import itmo.anasteshap.dto.responses.FleaResponse;
import itmo.anasteshap.dto.update.UpdateFleaRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FleaService {
    FleaResponse save(CreateFleaRequest fleaRequest);

    FleaResponse getById(Long id);

    void deleteById(Long id);

    FleaResponse update(UpdateFleaRequest fleaRequest);

    List<FleaResponse> getAll(Pageable pageable);

    List<FleaResponse> getAllByCatId(Long id);

    void deleteAll();
}
