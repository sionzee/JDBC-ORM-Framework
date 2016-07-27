package cz.ormframework.plugins.bukkit.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.sql.Statement;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class ExecuteQueryEvent extends Event implements Cancellable {
    private final static HandlerList handlerList = new HandlerList();

    private int id;
    private Statement statement;
    private String query;
    private StackTraceElement current;

    /**
     * Instantiates a new Execute query event.
     *
     * @param event the event
     */
    public ExecuteQueryEvent(cz.ormframework.events.objects.ExecuteQueryEvent event) {
        this.id = event.getQueryId();
        this.query = event.getQuery();
        this.current = event.getCurrent();
        this.statement = event.getStatement();
        this.setCancelled(event.isCancelled());
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
     * Gets query id.
     *
     * @return the query id
     */
    public int getQueryId() {
        return id;
    }

    /**
     * Gets statement.
     *
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Gets query.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    /**
     * Gets handler list.
     *
     * @return the handler list
     */
    public static HandlerList getHandlerList() {
        return handlerList;
    }

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean value) {
        cancelled = value;
    }
}