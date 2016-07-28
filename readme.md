JDBC ORM Framework 2.1
===================
Framework for administration entities.
This framework is created to be user-friendly and **easy** to use.

----------
Requirements
-------------
* Java 8
* **Brain** and time

Usage
-------------
#### <img align="left" src="https://cdn4.iconfinder.com/data/icons/6x16-free-application-icons/16/Refresh.png" />&nbsp;Init a database
```java
MySQL databaseInstance = new MySQL(host, database, user, password, port);
EntityManager entityManager = new EntityManager(databaseInstance);
```

> **Note:**

> - For a custom connection use custom class which implement IConnection
> - EntityManager is needed everywhere!


#### <img align="left" src="https://netbeans.org/projects/platform/sources/platform-content/content/trunk/images/tutorials/paintapp/70/new_icon.png" />&nbsp;Create an entity

```java
@Table
public class User {
	@Column private int id;
	@Column private Rank rank;
	@Column private String name;
	public int getId() {return this.id;}
	public void setName(String name) {this.name = name;}
	public void setRank(Rank rank){this.rank = rank;}
	public String getName() {return this.name;}
	public Rank getRank() {return this.rank;}
}
@Table
public class Rank {
	@Column private int id;
	@Column private String name;
	public int getId() {return this.id;}
	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}
}
```

> **Note:**

> - Entity have to have **@Table** annotation 
> - Every field which have to be in database must have **@Column** annotation. (**including id**)

#### <img align="left" src="http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/png/16x16/Create.png" />&nbsp;Create tables

```java
TableCreator tableCreator = entityManager.getTableCreator();
boolean recreateTables = false;

//Automatically
tableCreator.createAllTablesInJar(new File("PathToThisJar"), recreateTables);

//Manually
tableCreator.createTable(User.class, recreateTables);
tableCreator.createTable(Rank.class, recreateTables);
```
> **Note:**

> - Path to this Jar you can receive through: **JarUtils.getJarFile(EntityManager.class);**

#### <img align="left" src="http://files.softicons.com/download/toolbar-icons/16x16-free-toolbar-icons-by-aha-soft/png/16/add.png" />&nbsp;Insert into a table

```java
Rank rank = new Rank();
rank.setName("Administrator");

User user = new User();
user.setName("George");
user.setRank(rank);

//First persist rank and then user. Why? Firstly needs to be created all inner entities. Then theirs parents.
entityManager.persist(rank).persist(user).flush();
```

> **Note:**

> - On persist is automatically assigned ID to the user entity.

#### <img align="left" src="http://findicons.com/files/icons/949/token/16/search.png" />&nbsp;Select from a database
```java
User user = entityManager.getRepository(User.class).find().where("id = {0}", 1).ONE();
if(user != null) {
    print("User " + user.getName() + " have a rank " + user.getRank().getName());
    //User George have a rank Administrator
} else {
    print("User ID: " + 1 + " not found!"); 
    //User ID: 1 not found!
}
```


#### <img align="left" src="https://cdn2.iconfinder.com/data/icons/aspneticons_v1.0_Nov2006/edit_16x16.gif" />&nbsp;Modify value
```java
User user = entityManager.getRepository(User.class).find().where("id = {0}", 1).ONE();
user.setName("NewName");
entityManager.persist(user).flush();
```


#### <img align="left" src="https://cdn2.iconfinder.com/data/icons/aspneticons_v1.0_Nov2006/delete_16x16.gif" />&nbsp;Remove an entity
```java
User user = entityManager.getRepository(User.class).find().where("id = {0}", 1).ONE();
if(user != null) // Exists
	entityManager.delete(user);
	
//second way
entityManager.getRepository(User.class).delete().where("id = {0}", 1).one();

entityManager.flush();
```
> **Note:**

> - When calling a delete, don't forget for a flush!

#### Supported Types
* enum
* boolean
* String
* int
* long
* byte
* short
* float
* double
* Date (sql package)
* Timestamp (sql package)
* Time (sql package)
* char
* entity

And arrays of all supported types!
