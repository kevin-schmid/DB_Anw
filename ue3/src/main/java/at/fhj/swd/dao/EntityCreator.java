package at.fhj.swd.dao;

public interface EntityCreator<T extends Entity> {
    T create();
}
