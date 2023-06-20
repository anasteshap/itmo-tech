package itmo.anasteshap.services;

import itmo.anasteshap.dto.create.CreateOwnerRequest;
import itmo.anasteshap.dto.responses.OwnerResponse;
import itmo.anasteshap.dto.update.UpdateOwnerRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OwnerService {
    OwnerResponse save(CreateOwnerRequest ownerRequest);

    OwnerResponse getById(Long id);

    void deleteById(Long id);

    OwnerResponse update(UpdateOwnerRequest ownerRequest);

    List<OwnerResponse> getAll(Pageable pageable);

    void deleteAll();
}
