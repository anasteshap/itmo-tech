package itmo.anasteshap.dao.interfaces;

import itmo.anasteshap.entities.Owner;

import java.util.List;

public interface OwnerDao {
    Owner save(Owner owner);

    void deleteById(long id);

    void deleteByEntity(Owner owner);

    void deleteAll();

    Owner update(Owner owner);

    Owner getById(long id);

    List<Owner> getAll();
}
