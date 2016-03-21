# JDBC ORM Framework
**Project is in Development stage. [Checkout!](https://gitlab.bukva.cz/siOnzee/JDBC-ORM-FRAMEWORK/tree/develop)**

## Requirements
- Java 8 (_due to Lambda's functions :p_)
- Brain
- Time to learn


How to use
_Please do not use this in production, this is still development version. (Or use it on own risk)_
_Please report all issues. Thanks, __siOnzee__._

1.) Create MySQL Connection

```java
MySQL mySQL = MySQL.create("id connection", "host", "db", "user", "pass", 3306);
```

2.) Prepare EntityManager

```java
EntityManager entityManager = new EntityManager(MySQL.get("id connection"));
```

3.) Prepare Entities
Every entity have to implement _Entity_ and have to has field "id"
Every column which have to be in database, must have annotation _@Column_

```java
public class User implements Entity {

    @Column
    private int id;

    @Column
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public int getId() {
        return id;
    }
}
```

4.) Create Tables

```java
TableCreator tc = new TableCreator();
tc.createTables(mysql, User.class);
```

5.) Time for fun

Insert ....
```java
User user = new User();
user.setUsername("Foo");
entityManager.persist(user).flush();
print("ID: " + user.getId());
```

Select ....
```java
User user = entityManager.getRepository(User.class).findOneBy("username", "Foo");
print(user.getUsername();
print(user.getId());
```

Update ....
```java
User user = entityManager.getRepository(User.class).findOneBy("username", "Foo");
user.setUsername("Foo2");
entityManager.persist(user).flush();
```

6.) Links - **NOT WORK**

```java
class User implements Entity {

    @Column
    private int id;
    
    @Column
    @Linked(node="author_id", value="id")
    private Collection<Article> articles;
}

class Article implements Entity {

    @Column
    private int id;
    
    @Column
    private User author_id;
}
```
