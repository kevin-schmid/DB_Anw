package at.fhj.swd.persistence;

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

    public EntityManager getEntityManager() {
        return entityManager;
    }

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
        T result = entityManager.find(entityClass, id);
        // needed to ignore cache
        this.refresh(result);
        return result;
    }

    public <T extends Entity> List<T> findAll(Class<T> entityClass) {
        Query q = entityManager.createQuery("from "+entityClass.getSimpleName());
        final List<T> resultList = q.getResultList();
        // needed to ignore cache
        resultList.forEach(r -> this.refresh(r));
        return resultList;
    }

    public void remove(Entity entity) {
        inTransaction(() -> entityManager.remove(entity));
    }

    public void merge(Entity entity) {
        inTransaction(() -> entityManager.merge(entity));
    }

    public void refresh(Entity entity) {
        if(entity != null) {
            entityManager.refresh(entity);
        }
    }

    private void inTransaction(Runnable runnable) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        runnable.run();
        transaction.commit();
    }
}
