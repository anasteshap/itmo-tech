package itmo.anasteshap.mappers;

public interface Mapper<EntityClass, DtoClass> {
    EntityClass toEntity(DtoClass dto);
    DtoClass toDto(EntityClass entity);
}
