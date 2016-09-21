package cz.ormframework.builder;

import cz.ormframework.EntityManager;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class DefaultQueryBuilder extends QueryBuilder {
    /**
     * Instantiates a new Query builder.
     *
     * @param clazz         the clazz
     * @param entityManager the entity manager
     */
    public DefaultQueryBuilder(Class clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
