package cz.ormframework.plugins.bukkit.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.sql.ResultSet;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 *
 * @param <Type> the type parameter
 */
public class QueryDoneEvent<Type> extends Event {
    private final static HandlerList handlerList = new HandlerList();

    private int id;
    private Type type;
    private String query;
    private ResultSet resultSet;

    /**
     * Instantiates a new Query done event.
     *
     * @param event the event
     */
    public QueryDoneEvent(cz.ormframework.events.objects.QueryDoneEvent event) {
        this.id = event.getQueryId();
        this.resultSet = event.getResultSet();
        this.query = event.getQuery();
        this.type = (Type) event.getEntity();
    }

    /**
     * Gets result set.
     *
     * @return the result set
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Gets query id.
     *
     * @return the query id
     */
    public int getQueryId() {
        return id;
    }

    /**
     * Gets entity.
     *
     * @return the entity
     */
    public Type getEntity() {
        return type;
    }

    /**
     * Gets query.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Gets handler list.
     *
     * @return the handler list
     */
    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}