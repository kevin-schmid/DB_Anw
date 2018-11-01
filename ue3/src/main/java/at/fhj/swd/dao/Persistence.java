package at.fhj.swd.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public enum Persistence {
    INST;
    private EntityManager entityManager;
    EntityManagerFactory managerFactory;

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
