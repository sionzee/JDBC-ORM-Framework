package test.cz.romframework.repositories;

import cz.ormframework.EntityManager;
import cz.ormframework.repositories.Repository;
import test.cz.romframework.codeexamples.manager.User;

/**
 * Created by siOnzee on 20.09.2016.
 */
public class UserRepository extends Repository<User> {
    /**
     * Instantiates a new Repository.
     *
     * @param clazz         the clazz
     * @param entityManager the entity manager
     */
    public UserRepository(Class<User> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public User getUserByName(String username) {
        return this.find().where("username = {0}", username).one();
    }
}
