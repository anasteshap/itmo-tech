package itmo.anasteshap.mappers;

import itmo.anasteshap.dto.OwnerDto;
import itmo.anasteshap.entities.Owner;

public class OwnerMapper implements Mapper<Owner, OwnerDto> {
    @Override
    public Owner toEntity(OwnerDto dto) {
        var owner = new Owner(dto.name(), dto.birthDate());
        owner.setId(dto.id());
        return owner;
    }

    @Override
    public OwnerDto toDto(Owner entity) {
        return new OwnerDto(entity.getId(), entity.getName(), entity.getBirthDate());
    }
}
