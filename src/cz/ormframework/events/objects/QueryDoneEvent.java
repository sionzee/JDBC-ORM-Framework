package cz.ormframework.events.objects;

import cz.ormframework.events.Event;
import cz.ormframework.events.HandlerList;

import java.sql.ResultSet;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 *
 * @param <Type> the type parameter
 */
public class QueryDoneEvent<Type> extends Event {
    private final static HandlerList<QueryDoneEvent> handlerList = new HandlerList<>();

    private int id;
    private Type type;
    private String query;
    private ResultSet resultSet;

    /**
     * Instantiates a new Query done event.
     *
     * @param queryID the query id
     * @param type    the type
     * @param query   the query
     */
    public QueryDoneEvent(int queryID, Type type, String query) {
        this.id = queryID;
        this.type = type;
        this.query = query;
    }

    /**
     * Instantiates a new Query done event.
     *
     * @param queryID   the query id
     * @param resultSet the result set
     * @param query     the query
     */
    public QueryDoneEvent(int queryID, ResultSet resultSet, String query) {
        this.id = queryID;
        this.resultSet = resultSet;
        this.query = query;
    }

    /**
     * ResultSet if used to query
     *
     * @return null or result
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * UnicateID of Query
     *
     * @return id query id
     */
    public int getQueryId() {
        return id;
    }

    /**
     * Gets entity.
     *
     * @return Updated entity
     */
    public Type getEntity() {
        return type;
    }

    /**
     * Gets query.
     *
     * @return Query what was executed
     */
    public String getQuery() {
        return query;
    }

    /**
     * HandlerList for registering events
     *
     * @return HandlerList handler list
     */
    public static HandlerList<QueryDoneEvent> getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
