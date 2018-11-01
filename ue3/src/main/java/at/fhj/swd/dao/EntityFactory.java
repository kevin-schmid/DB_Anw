package at.fhj.swd.dao;

public abstract class EntityFactory<T extends Entity> implements EntityCreator<T>{
    protected abstract T createEntity();

    @Override
    public T create() {
        T entity = createEntity();
        Persistence.INST.persist(entity);
        return entity;
    }
}
