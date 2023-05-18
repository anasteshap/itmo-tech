package itmo.anasteshap.dao.hibernate;

import itmo.anasteshap.dao.interfaces.OwnerDao;
import itmo.anasteshap.entities.Owner;
import itmo.anasteshap.util.HibernateSessionFactory;

import java.util.List;

public class HibernateOwnerDao implements OwnerDao {
    @Override
    public Owner save(Owner owner) {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(owner);
            session.getTransaction().commit();
        }
        return owner;
    }

    @Override
    public void deleteById(long id) {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete from Owner where id = :id", Owner.class)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteByEntity(Owner owner) {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(owner);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteAll() {
        HibernateSessionFactory.getSessionFactory().openSession().createQuery("delete from Owner", Owner.class).executeUpdate();
    }

    @Override
    public Owner update(Owner owner) {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(owner); // or merge (вызывает insert/update)
            session.getTransaction().commit();
        }
        return owner;
    }

    @Override
    public Owner getById(long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Owner.class, id);
    }

    @Override
    public List<Owner> getAll() {
        return null;
    }
}
