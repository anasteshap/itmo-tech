package itmo.anasteshap.dao.mybatis;

import itmo.anasteshap.dao.interfaces.OwnerDao;
import itmo.anasteshap.dao.mybatis.mappers.MyBatisOwnerMapper;
import itmo.anasteshap.entities.Owner;
import itmo.anasteshap.util.MyBatisSessionFactory;

import java.util.List;

public class MyBatisOwnerDao implements OwnerDao {
    @Override
    public Owner save(Owner owner) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisOwnerMapper.class);
            mapper.save(owner);
            session.commit();
            return owner;
        }
    }

    @Override
    public void deleteById(long id) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisOwnerMapper.class);
            mapper.deleteById(id);
            session.commit();
        }
    }

    @Override
    public void deleteByEntity(Owner owner) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisOwnerMapper.class);
            mapper.deleteByEntity(owner);
            session.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisOwnerMapper.class);
            mapper.deleteAll();
            session.commit();
        }
    }

    @Override
    public Owner update(Owner owner) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisOwnerMapper.class);
            mapper.update(owner);
            session.commit();
            return owner;
        }
    }

    @Override
    public Owner getById(long id) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisOwnerMapper.class);
            var owner = mapper.getById(id);
            session.commit();
            return owner;
        }
    }

    public List<Owner> getAll() {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisOwnerMapper.class);
            var owners = mapper.getAll();
            session.commit();
            return owners;
        }
    }
}
