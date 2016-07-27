JDBC ORM Framework 2.1
===================
Framework for administrace entities.
This framework is created to be user-friendly and **easy** to use.

----------
Requirements
-------------
* Java 8
* **Brain** and time

Usage
-------------
#### 	![](https://cdn4.iconfinder.com/data/icons/6x16-free-application-icons/16/Refresh.png) Init a database
```java
MySQL databaseInstance = new MySQL(host, database, user, password, port);
EntityManager entityManager = new EntityManager(databaseInstance);
```

> **Note:**

> - For a custom connection use custom class which implement IConnection
> - EntityManager is needed everywere!


#### 	![](https://netbeans.org/projects/platform/sources/platform-content/content/trunk/images/tutorials/paintapp/70/new_icon.png) Create a entity

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

#### 	![](http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/png/16x16/Create.png) Create tables

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

#### 	![](http://files.softicons.com/download/toolbar-icons/16x16-free-toolbar-icons-by-aha-soft/png/16/add.png) Insert into a table

```java
Rank rank = new Rank();
rank.setName("Administrator");

User user = new User();
user.setName("George");
user.setRank(rank);

//First persist rank and then user. Why? Firstly needs to be created all inner entities. Then theirs parents.
entityManager.persist(rank).persist(user).flush();
```

#### 	![](http://files.softicons.com/download/system-icons/web0.2ama-icons-by-chrfb/png/16x16/Search.png) Select from a database
```java
User user = entityManager.getRepository(User.class).Find().Where("id = {0}", 1).ONE();
print("User " + user.getName() + " have a rank " + user.getRank().getName());
//User George have a rank Administrator
```


#### 	![](https://cdn2.iconfinder.com/data/icons/aspneticons_v1.0_Nov2006/edit_16x16.gif) Modify value
```java
User user = entityManager.getRepository(User.class).Find().Where("id = {0}", 1).ONE();
user.setName("NewName");
entityManager.persist(user).flush();
```


#### 	![](http://files.softicons.com/download/toolbar-icons/16x16-free-toolbar-icons-by-aha-soft/png/16/delete-2.png) Remove entity
```java
User user = entityManager.getRepository(User.class).Find().Where("id = {0}", 1).ONE();
if(user != null) // Exists
	entityManager.delete(user);
```
> **Note:**

> - When calling a delete, flush is automatically called too.

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
* Entity

And arrays of all supported types!
