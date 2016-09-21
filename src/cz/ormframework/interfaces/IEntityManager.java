package cz.ormframework.interfaces;

import com.sun.istack.internal.NotNull;
import cz.ormframework.database.Database;
import cz.ormframework.repositories.Repository;
import cz.ormframework.tools.TableCreator;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public interface IEntityManager {
    /**
     * Persiste a Entity, to commit need to call flush();
     *
     * @param <Type> Return Entity
     * @param entities Entities
     * @return Returns IEntityManager for next persists or flushes.
     */
    <Type> IEntityManager persist(@NotNull Type... entities);

    /**
     * Return a repository for a Entity
     *
     * @param <Type> Return Entity
     * @param clazz  Entity.class
     * @return Repostiroy for a entity
     */
    <Type> Repository getRepository(@NotNull Class<Type> clazz);

    /**
     * Commit all changes to the database
     */
    void flush();

    /**
     * Return a table creator
     *
     * @return table creator
     */
    TableCreator getTableCreator();

    /**
     * Return a connection
     *
     * @return connection database
     */
    Database getDatabase();

    /**
     * Open or ReOpen closed connection
     */
    void reopenConnectionOnClose();

    <EntityType, Type extends Repository<EntityType>> Type registerRepository(Class<Type> repositoryClass, Class<EntityType> entityClass);
}
