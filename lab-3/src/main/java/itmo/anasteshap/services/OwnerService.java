package itmo.anasteshap.services;

import itmo.anasteshap.dto.create.CreateOwnerRequest;
import itmo.anasteshap.dto.responses.OwnerResponse;
import itmo.anasteshap.dto.update.UpdateOwnerRequest;
import itmo.anasteshap.entities.Owner;
import itmo.anasteshap.exceptions.NotFoundException;
import itmo.anasteshap.exceptions.PayloadTooLargeException;
import itmo.anasteshap.repositories.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, ModelMapper modelMapper) {
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    public OwnerResponse save(CreateOwnerRequest ownerRequest) {
        var entity = modelMapper.map(ownerRequest, Owner.class);
        ownerRepository.save(entity);
        return modelMapper.map(entity, OwnerResponse.class);
    }

    public OwnerResponse getById(Long id) {
        var entity = ownerRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        return modelMapper.map(entity, OwnerResponse.class);
    }

    public void deleteById(Long id) {
        var entity = ownerRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        ownerRepository.delete(entity);
    }

    public OwnerResponse update(UpdateOwnerRequest ownerRequest) {
        var entity = ownerRepository
                .findById(ownerRequest.getId())
                .orElseThrow(NotFoundException::new); // написать что такого хозяина нет

        entity.setName(ownerRequest.getName());
        entity.setBirthDate(ownerRequest.getBirthDate());
        ownerRepository.save(entity);
        return modelMapper.map(entity, OwnerResponse.class);
    }

    public List<OwnerResponse> getAll(Pageable pageable) {
        try {
            var owners = ownerRepository.findAll(pageable).getContent();
            return owners.stream().map(cat -> modelMapper.map(cat, OwnerResponse.class)).toList();
        }
        catch (RuntimeException e) {
            throw new PayloadTooLargeException(e.getMessage());
        }
    }

    public void deleteAll() {
        ownerRepository.deleteAll();
    }
}
