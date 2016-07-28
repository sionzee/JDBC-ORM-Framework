package test.cz.romframework.codeexamples.manager;

import cz.ormframework.EntityManager;
import cz.ormframework.log.Debug;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * siOnzee.cz
 * JDBC ORM Framework
 * Created 28.07.2016
 */
public class UserManager {

    HashMap<String, User> users;
    EntityManager em;

    public UserManager(EntityManager em) {
        this.em = em;
        this.users = new HashMap<String, User>();
        this.em.registerDeleteOn(User.class, this::onDelete);
    }

    private void onDelete(User u) {
        users.remove(u.getUsername());
    }

    public void addUser(String username) {
        User u = em.getRepository(User.class).find().where("username = {0}", username).one();
        if(u != null) {
            users.put(username, u);
        } else {
            u = new User();
            u.setUsername(username);
            u.setAge(new Random().nextInt(120));
            em.persist(u);
        }
    }

    public void removeUser(User u) {
        em.delete(u); // call our callback onDelete
    }

    public Stream<User> filter(Predicate<User> u) {
        return users.values().stream().filter(u);
    }
}
