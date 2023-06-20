package itmo.anasteshap.services;

import itmo.anasteshap.dto.create.CreateCatRequest;
import itmo.anasteshap.dto.responses.CatResponse;
import itmo.anasteshap.dto.update.UpdateCatRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatService {
    CatResponse save(CreateCatRequest catRequest);

    CatResponse getById(Long id);

    void deleteById(Long id);

    CatResponse update(UpdateCatRequest catRequest);

    List<CatResponse> getAll(Pageable pageable);

    List<CatResponse> getAllByOwnerId(Long id);

    void deleteAll();
}
