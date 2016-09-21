package cz.ormframework;

import com.sun.istack.internal.NotNull;
import cz.ormframework.annotations.Table;
import cz.ormframework.database.Database;
import cz.ormframework.events.EventManager;
import cz.ormframework.events.objects.*;
import cz.ormframework.interfaces.IEntityManager;
import cz.ormframework.log.Debug;
import cz.ormframework.parsers.Parser;
import cz.ormframework.repositories.Repository;
import cz.ormframework.tools.TableCreator;
import cz.ormframework.utils.EntityUtils;
import cz.ormframework.utils.Formatter;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class EntityManager implements IEntityManager {

    private int queryID = 0;
    private final HashMap<String, Repository<?>> cache = new HashMap<>();
    private Database database;
    private PreparedStatement statement;
    private TableCreator tableCreator;
    private int queryId;
    private boolean enableEvents = true;

    @Override
    public TableCreator getTableCreator() {
        return tableCreator;
    }

    /**
     * Instantiates a new Entity manager.
     *
     * @param database the database
     */
    public EntityManager(Database database) {
        if(!database.validateClasses()) {
            Debug.error("Valid classes not found for connection " + database.getClass().getSimpleName());
            return;
        }

        if (Thread.currentThread().getName().equalsIgnoreCase("Server thread") || Thread.currentThread().getId() == 0)
            Debug.warning("EntityManager new instance detected on Main Thread!\nPlease use another Thread! (Protection before lags caused by connection)");
        this.database = database;
        this.tableCreator = new TableCreator(this);
        try {
            reopenConnectionOnClose();
            getDatabase().getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            Debug.exception(e);
        }
    }

    /**
     * Instantiates a new Entity manager.
     *
     * @param database the database
     */
    public EntityManager(Database database, boolean enableEvents) {
        this(database);
        this.enableEvents = enableEvents;
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public void reopenConnectionOnClose() {
        if(getDatabase().isClosed())
            try {
                if(!getDatabase().openConnection())
                    Debug.error("Cannot open connection.");
                else
                    getDatabase().getConnection().setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private <Type> IEntityManager insertEntity(Type entity, String table) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Arrays.stream(entity.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            String column = EntityUtils.getColumnName(field);

            if (column != null && !column.equalsIgnoreCase("id")) {
                try {
                    Table ent = field.getType().getDeclaredAnnotation(Table.class);
                    if (ent == null) {
                        columns.append("`").append(column).append("`").append(", ");
                        values.append("'").append(Parser.EntityToDBType(field, entity)).append("'").append(", ");
                    } else {
                        int idd = EntityUtils.getId(field.get(entity));
                        if (idd > 0) {
                            columns.append("`").append(column).append("`").append(", ");
                            values.append("'").append(idd).append("'").append(", ");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        });

        String result = Formatter.format("INSERT INTO `{0}` ({1}) VALUES ({2})", table, columns.substring(0, columns.length() - 2), values.substring(0, values.length() - 2));
        if(enableEvents) {
            EntityInsertEvent executeQueryEvent = new EntityInsertEvent(queryID, Thread.currentThread().getStackTrace(), result, statement, entity);
            EventManager.FireEvent(executeQueryEvent);
            if (executeQueryEvent.isCancelled()) {
                return this;
            }
        }
        try {

            try {
                this.statement = getDatabase().getConnection().prepareStatement(result, Statement.RETURN_GENERATED_KEYS);
            } catch (SQLException e) {
                Debug.exception(e);
            }

            statement.execute(result, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            EntityUtils.setId(entity, rs.getInt(1));
        } catch (SQLException e) {
            Debug.exception(e);
        }
        if(enableEvents) {
            QueryDoneEvent<Type> queryDoneEvent = new QueryDoneEvent<>(queryID, entity, result);
            EventManager.FireEvent(queryDoneEvent);
        }
        return this;
    }

    private <Type> IEntityManager updateEntity(int id, Type entity, String table) {
        StringBuilder changes = new StringBuilder();
        Object databaseEntity = getRepository(entity.getClass()).find().where("id = {0}", id).one();
        Arrays.stream(entity.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            String columnName = EntityUtils.getColumnName(field);
            if (columnName != null) {
                try {
                    boolean areEquals = EntityUtils.compareTwoFields(field, entity, databaseEntity);
                    if (!areEquals) {
                        field.setAccessible(true);
                        Object value = Parser.ToDBType(field, field.get(entity));
                        field.setAccessible(false);

                        if (value != null) {
                            changes.append("`").append(columnName).append("` = '").append(value).append("'").append(", ");
                        } else
                            changes.append("`").append(columnName).append("` = NULL").append(", ");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        if (changes.length() < 1)
            return this;
        String query = Formatter.format("UPDATE `{0}` SET {1} where {2}", table, changes.substring(0, changes.length() - 2), "`id` = '" + id + "'");
        if(enableEvents) {
            EntityUpdateEvent executeQueryEvent = new EntityUpdateEvent(queryID, Thread.currentThread().getStackTrace(), query, statement, databaseEntity, entity);
            EventManager.FireEvent(executeQueryEvent);
            if (executeQueryEvent.isCancelled()) {
                return this;
            }
        }

        try {

            try {
                this.statement = getDatabase().getConnection().prepareStatement(query);
            } catch (SQLException e) {
                Debug.exception(e);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            Debug.exception(e);
        }
        QueryDoneEvent<Type> queryDoneEvent = new QueryDoneEvent<>(queryID, entity, query);
        EventManager.FireEvent(queryDoneEvent);
        return this;
    }

    @SafeVarargs
    @Override
    public final <Type> IEntityManager persist(@NotNull Type... entities) {

        reopenConnectionOnClose();

        for (Type entity : entities) {
            queryID++;
            String table = EntityUtils.getTable(entity.getClass());
            int id = EntityUtils.getId(entity);
            boolean update = id > 0;
            if (update) {
                return updateEntity(id, entity, table);
            } else {
                return insertEntity(entity, table);
            }
        }
        return this;
    }

    /**
     * Delete entity manager.
     *
     * @param <Type> the type parameter
     * @param entity the entity
     * @return the entity manager
     */
    public <Type> EntityManager delete(@NotNull Type entity) {
        Class<?> clazz = entity.getClass();
        if (!EntityUtils.isEntity(clazz)) {
            Debug.error(clazz.getCanonicalName() + " isn't entity! Can't delete!");
            return this;
        }

        if (EntityUtils.getId(entity) < 1) {
            Debug.error(clazz.getCanonicalName() + " isn't in database! Can't delete!");
            return this;
        }

        String result = "DELETE from `" + EntityUtils.getTable(entity.getClass()) + "` where `id` = '" + EntityUtils.getId(entity) + "'";

        if(enableEvents) {
            EntityDeleteEvent executeQueryEvent = new EntityDeleteEvent(queryID, Thread.currentThread().getStackTrace(), result, statement, entity);
            EventManager.FireEvent(executeQueryEvent);
            if (executeQueryEvent.isCancelled()) {
                return this;
            }
        }


        reopenConnectionOnClose();

        try {
            this.statement = getDatabase().getConnection().prepareStatement(result);
            this.statement.execute();
        } catch (SQLException e) {
            Debug.exception(e);
        }
        QueryDoneEvent<Type> queryDoneEvent = new QueryDoneEvent<>(queryID, entity, result);
        EventManager.FireEvent(queryDoneEvent);
        return this;
    }

    private boolean isStatementClosed() {
        try {
            return statement == null || statement.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private PreparedStatement createPreparedStatement() {
        try {
            return getDatabase().getConnection().prepareStatement("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <EntityType, Type extends Repository<EntityType>> Type registerRepository(Class<Type> repositoryClass, Class<EntityType> entityClass) {
        try {
            Type repositoryInstance = repositoryClass.getDeclaredConstructor(Class.class, EntityManager.class).newInstance(entityClass, this);

            if(enableEvents) {
                RegisterRepositoryEvent event = new RegisterRepositoryEvent(repositoryClass, entityClass);
                EventManager.FireEvent(event);
                if(event.isCancelled())
                    return null;
            }

            cache.put(EntityUtils.getTable(entityClass), repositoryInstance);
            return (Type) cache.get(EntityUtils.getTable(entityClass));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type> Repository<Type> getRepository(@NotNull Class<Type> clazz) {
        if (clazz == null) {
            try {
                throw new Exception("getRepository(null) cannot be executed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        reopenConnectionOnClose();

        if(isStatementClosed())
            if((this.statement = createPreparedStatement()) == null) {
                try {
                    throw new Exception("createPreparedStatement() returned null, cannot open Statement");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        String table = EntityUtils.getTable(clazz);

        if (cache.containsKey(table))
            return (Repository<Type>) cache.<Type>get(table);

        cache.put(table, new Repository<Type>(clazz, this));
        return (Repository<Type>) cache.<Type>get(table);
    }

    @Override
    public void flush() {

        if(enableEvents) {
            QueryFlushEvent event = new QueryFlushEvent(statement);
            EventManager.FireEvent(event);
            if(event.isCancelled())
                return;
        }

        reopenConnectionOnClose();

        try {

            statement.executeBatch();
            statement.clearBatch();
            getDatabase().getConnection().commit();
        } catch (SQLException e) {
            Debug.exception(e);
        } finally {
            try {
                statement.close();
                getDatabase().close();
            } catch (SQLException e) {
                Debug.exception(e);
            }
        }
    }

    /**
     * Gets query id.
     *
     * @return the query id
     */
    public int getQueryId() {
        return queryId++;
    }
}
