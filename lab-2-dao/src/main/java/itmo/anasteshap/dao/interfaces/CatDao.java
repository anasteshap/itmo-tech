package itmo.anasteshap.dao.interfaces;

import itmo.anasteshap.entities.Cat;

import java.util.List;

public interface CatDao {
    Cat save(Cat cat);

    void deleteById(long id);

    void deleteByEntity(Cat cat);

    void deleteAll();

    Cat update(Cat cat);

    Cat getById(long id);

    List<Cat> getAll();

    List<Cat> getAllByOwnerId(long id);
}
