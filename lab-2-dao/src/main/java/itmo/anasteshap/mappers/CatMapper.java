package itmo.anasteshap.mappers;

import itmo.anasteshap.dto.CatDto;
import itmo.anasteshap.entities.Cat;

public class CatMapper implements Mapper<Cat, CatDto> {
    @Override
    public Cat toEntity(CatDto dto) {
        var cat = new Cat(dto.name(), dto.birthDate(), dto.breed(), dto.color());
        cat.setId(dto.id());
        cat.setOwner(new OwnerMapper().toEntity(dto.owner())); // или лучше хранить owner_id в catDto?
        return cat;
    }

    @Override
    public CatDto toDto(Cat entity) {
        return new CatDto(
                entity.getId(),
                entity.getName(),
                entity.getBirthDate(),
                entity.getBreed(),
                entity.getColor(),
                new OwnerMapper().toDto(entity.getOwner()));
    }
}
