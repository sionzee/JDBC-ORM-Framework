package cz.ormframework.interfaces;

/**
 * siOnzee.cz
 * JDBC ORM Framework
 * Created 28.07.2016
 */
public interface ICallback<Type> {
    void execute(Type entity);
}
