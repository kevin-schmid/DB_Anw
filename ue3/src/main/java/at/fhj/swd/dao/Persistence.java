package at.fhj.swd.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public enum Persistence {
    INST;
    private EntityManager entityManager;
    private EntityManagerFactory managerFactory;

    Persistence(){}

    public void create(String persistenceName) {
        managerFactory = javax.persistence.Persistence.createEntityManagerFactory(persistenceName);
        entityManager = managerFactory.createEntityManager();
    }

    public void persist(Entity entity) {
        inTransaction(() -> entityManager.persist(entity));
    }

    public void close() {
        entityManager.close();
        managerFactory.close();
    }

    public <T extends Entity> T find(Class<T> entityClass, int id) {
        return entityManager.find(entityClass, id);
    }

    public <T extends Entity> List<T> findAll(Class<T> entityClass) {
        Query q = entityManager.createQuery("from "+entityClass.getSimpleName());
        return q.getResultList();
    }

    public void remove(Entity entity) {
        inTransaction(() -> entityManager.remove(entity));
    }

    public void inTransaction(Runnable runnable) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        runnable.run();
        transaction.commit();
    }
}
