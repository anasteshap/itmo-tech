package itmo.anasteshap.dao.hibernate;

import itmo.anasteshap.dao.interfaces.CatDao;
import itmo.anasteshap.entities.Cat;
import itmo.anasteshap.util.HibernateSessionFactory;
import org.hibernate.Session;

import java.util.List;

public class HibernateCatDao implements CatDao {
    @Override
    public Cat save(Cat cat) {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(cat);
            session.getTransaction().commit();
        }
        return cat;
    }

    @Override
    public void deleteById(long id) {
        HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("delete from Cat where id = :id", Cat.class)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void deleteByEntity(Cat cat) {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(cat);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteAll() {
        HibernateSessionFactory.getSessionFactory().openSession().createQuery("delete from Cat", Cat.class).executeUpdate();
//        HibernateSessionFactory.getSessionFactory().openSession().createQuery("alter SEQUENCE Cat.sequence RESTART WITH 1").executeUpdate();
    }

    @Override
    public Cat update(Cat cat) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(cat);
            session.getTransaction().commit();
        }
        return cat;
    }

    @Override
    public Cat getById(long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Cat.class, id);
    }

    @Override
    public List<Cat> getAll() {
        return HibernateSessionFactory.getSessionFactory().openSession()
                .createQuery("select c from Cat c", Cat.class).getResultList();
    }

    @Override
    public List<Cat> getAllByOwnerId(long id) {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            var cats = session
                    .createQuery("select c from Cat c where Owner.id = :id", Cat.class)
                    .setParameter("id", id)
                    .getResultList();
            session.getTransaction().commit();
            return cats;
        }
    }
}
