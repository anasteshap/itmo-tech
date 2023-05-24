package itmo.anasteshap.services;

import itmo.anasteshap.dto.create.CreateCatRequest;
import itmo.anasteshap.dto.responses.CatResponse;
import itmo.anasteshap.dto.update.UpdateCatRequest;
import itmo.anasteshap.entities.Cat;
import itmo.anasteshap.exceptions.NotFoundException;
import itmo.anasteshap.exceptions.PayloadTooLargeException;
import itmo.anasteshap.repositories.CatRepository;
import itmo.anasteshap.repositories.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatService {
    private final CatRepository catRepository;
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CatService(CatRepository catRepository, ModelMapper modelMapper, OwnerRepository ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    public CatResponse save(CreateCatRequest catRequest) {
        var owner = ownerRepository
                .findById(catRequest.getOwnerId())
                .orElseThrow(NotFoundException::new); // написать что такого хозяина нет

        var entity = modelMapper.map(catRequest, Cat.class);
        entity.setOwner(owner);
        catRepository.save(entity);
        return modelMapper.map(entity, CatResponse.class);
    }

    public CatResponse getById(Long id) {
        var entity = catRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        return modelMapper.map(entity, CatResponse.class);
    }

    public void deleteById(Long id) {
        var entity = catRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        catRepository.delete(entity);
    }

    public CatResponse update(UpdateCatRequest catRequest) {
        var owner = ownerRepository
                .findById(catRequest.getOwnerId())
                .orElseThrow(NotFoundException::new); // написать что такого хозяина нет

        var cat = catRepository
                .findById(catRequest.getId())
                .orElseThrow(NotFoundException::new);

        cat.setName(catRequest.getName());
        cat.setBreed(catRequest.getBreed());
        cat.setColor(catRequest.getColor());
        cat.setBirthDate(catRequest.getBirthDate());
        cat.setTailLength(catRequest.getTailLength());
        cat.setOwner(owner);

        catRepository.save(cat);
        return modelMapper.map(cat, CatResponse.class);
    }

    public List<CatResponse> getAll(Pageable pageable) {
        try {
            var cats = catRepository.findAll(pageable).getContent();
            return cats.stream().map(cat -> modelMapper.map(cat, CatResponse.class)).toList();
        } catch (RuntimeException e) {
            throw new PayloadTooLargeException(e.getMessage());
        }
    }

    public List<CatResponse> getAllByOwnerId(Long id) {
        var cats = catRepository.findAllByOwnerId(id);
        return cats.stream().map(cat -> modelMapper.map(cat, CatResponse.class)).toList();
    }

    public void deleteAll() {
        catRepository.deleteAll();
    }
}
