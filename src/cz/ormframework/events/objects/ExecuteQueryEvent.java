package cz.ormframework.events.objects;

import cz.ormframework.events.Event;
import cz.ormframework.events.HandlerList;
import cz.ormframework.events.interfaces.Cancellable;

import java.sql.Statement;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class ExecuteQueryEvent extends Event implements Cancellable {
    private final static HandlerList<ExecuteQueryEvent> handlerList = new HandlerList<>();

    private int id;
    private Statement statement;
    private String query;
    private StackTraceElement current;

    /**
     * Instantiates a new Execute query event.
     *
     * @param queryID   the query id
     * @param current   the current
     * @param query     the query
     * @param statement the statement
     */
    public ExecuteQueryEvent(int queryID, StackTraceElement current, String query, Statement statement) {
        this.id = queryID;
        this.current = current;
        this.query = query;
        this.statement = statement;
    }

    /**
     * Gets current.
     *
     * @return the current
     */
    public StackTraceElement getCurrent() {
        return current;
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
     * Gets statement.
     *
     * @return Statement statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Gets query.
     *
     * @return Query which will be executed
     */
    public String getQuery() {
        return query;
    }

    @Override
    public HandlerList<ExecuteQueryEvent> getHandlers() {
        return handlerList;
    }

    /**
     * HandlerList for registering events
     *
     * @return HandlerList handler list
     */
    public static HandlerList<ExecuteQueryEvent> getHandlerList() {
        return handlerList;
    }

    private boolean cancelled;

    /**
     * @return true if event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Allow to cancel execution
     * @param value true if cancelled
     */
    @Override
    public void setCancelled(boolean value) {
        cancelled = value;
    }
}
