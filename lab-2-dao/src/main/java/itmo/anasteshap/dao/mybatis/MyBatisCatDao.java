package itmo.anasteshap.dao.mybatis;

import itmo.anasteshap.dao.interfaces.CatDao;
import itmo.anasteshap.dao.mybatis.mappers.MyBatisCatMapper;
import itmo.anasteshap.entities.Cat;
import itmo.anasteshap.util.MyBatisSessionFactory;

import java.util.List;

public class MyBatisCatDao implements CatDao {
    @Override
    public Cat save(Cat cat) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            mapper.save(cat);
            session.commit();
            return cat;
        }
    }

    @Override
    public void deleteById(long id) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            mapper.deleteById(id);
            session.commit();
        }
    }

    @Override
    public void deleteByEntity(Cat cat) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            mapper.deleteByEntity(cat);
            session.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            mapper.deleteAll();
            session.commit();
        }
    }

    @Override
    public Cat update(Cat cat) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            mapper.update(cat);
            session.commit();
            return cat;
        }
    }

    @Override
    public Cat getById(long id) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            var cat = mapper.getById(id);
            session.commit();
            return cat;
        }
    }

    @Override
    public List<Cat> getAll() {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            var cats = mapper.getAll();
            session.commit();
            return cats;
        }
    }

    @Override
    public List<Cat> getAllByOwnerId(long id) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            var mapper = session.getMapper(MyBatisCatMapper.class);
            var cats = mapper.getAllByOwnerId(id);
            session.commit();
            return cats;
        }
    }
}
