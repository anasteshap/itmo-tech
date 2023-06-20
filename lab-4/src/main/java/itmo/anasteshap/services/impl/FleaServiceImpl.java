package itmo.anasteshap.services.impl;

import itmo.anasteshap.dto.create.CreateFleaRequest;
import itmo.anasteshap.dto.responses.FleaResponse;
import itmo.anasteshap.dto.update.UpdateFleaRequest;
import itmo.anasteshap.entities.Flea;
import itmo.anasteshap.exceptions.NotFoundException;
import itmo.anasteshap.exceptions.PayloadTooLargeException;
import itmo.anasteshap.repositories.CatRepository;
import itmo.anasteshap.repositories.FleaRepository;
import itmo.anasteshap.services.FleaService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FleaServiceImpl implements FleaService {
    private final FleaRepository fleaRepository;
    private final CatRepository catRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FleaServiceImpl(FleaRepository fleaRepository, ModelMapper modelMapper, CatRepository catRepository) {
        this.fleaRepository = fleaRepository;
        this.catRepository = catRepository;
        this.modelMapper = modelMapper;
    }

    public FleaResponse save(CreateFleaRequest fleaRequest) {
        var cat = catRepository
                .findById(fleaRequest.getCatId())
                .orElseThrow(NotFoundException::new); // написать что такого кота нет

        var entity = modelMapper.map(fleaRequest, Flea.class);
        entity.setCat(cat);
        fleaRepository.save(entity);
        return modelMapper.map(entity, FleaResponse.class);
    }

    public FleaResponse getById(Long id) {
        var entity = fleaRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        return modelMapper.map(entity, FleaResponse.class);
    }

    public void deleteById(Long id) {
        var entity = fleaRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        fleaRepository.delete(entity);
    }

    @Transactional
    public FleaResponse update(UpdateFleaRequest fleaRequest) {
        var cat = catRepository
                .findById(fleaRequest.getCatId())
                .orElseThrow(NotFoundException::new); // написать что такого кота нет

        var flea = fleaRepository
                .findById(fleaRequest.getId())
                .orElseThrow(NotFoundException::new);

        flea.setName(flea.getName());
        flea.setCat(cat);

        fleaRepository.save(flea);
        return modelMapper.map(flea, FleaResponse.class);
    }

    public List<FleaResponse> getAll(Pageable pageable) {
        try {
            var fleas = fleaRepository.findAll(pageable).getContent();
            return fleas.stream().map(flea -> modelMapper.map(flea, FleaResponse.class)).toList();
        } catch (RuntimeException e) {
            throw new PayloadTooLargeException(e.getMessage());
        }
    }

    public List<FleaResponse> getAllByCatId(Long id) {
        var fleas = fleaRepository.findAllByCatId(id);
        return fleas.stream().map(flea -> modelMapper.map(flea, FleaResponse.class)).toList();
    }

    public void deleteAll() {
        fleaRepository.deleteAll();
    }
}
