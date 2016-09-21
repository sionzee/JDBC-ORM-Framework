package test.cz.ormframework.codeexamples.manager;

import cz.ormframework.annotations.Column;
import cz.ormframework.annotations.Table;

/**
 * siOnzee.cz
 * JDBC ORM Framework
 * Created 28.07.2016
 */
@Table
public class User
{

    @Column
    private int id, age;

    @Column
    private String username;

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
