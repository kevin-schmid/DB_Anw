package at.fhj.swd.persistence;

public interface EntityCreator<T extends Entity> {
    T create();
}
