package itmo.anasteshap;

import itmo.anasteshap.dao.hibernate.HibernateOwnerDao;
import itmo.anasteshap.dao.interfaces.OwnerDao;
import itmo.anasteshap.dao.jdbc.JdbcOwnerDao;
import itmo.anasteshap.dao.mybatis.MyBatisOwnerDao;
import itmo.anasteshap.entities.Owner;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var owner = new Owner("owner_name3", LocalDate.now());
        System.out.println(saveEntities(new HibernateOwnerDao(), owner));
        System.out.println(saveEntities(new JdbcOwnerDao(), owner));
        System.out.println(saveEntities(new MyBatisOwnerDao(), owner));
    }

    private static long saveEntities(OwnerDao owners, Owner owner) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            owners.save(owner);
        }

        long finish = System.currentTimeMillis();
        return finish - start;
    }
}