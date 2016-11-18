package cz.ormframework.events.objects;

import cz.ormframework.events.Event;
import cz.ormframework.events.HandlerList;
import cz.ormframework.events.interfaces.Cancellable;

import java.sql.Statement;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class QueryCreateTableEvent extends Event implements Cancellable {
    private final static HandlerList<QueryCreateTableEvent> handlerList = new HandlerList<>();
    private Statement statement;
    private boolean cancel;
    private int queryId;
    private StackTraceElement[] stackTrace;
    private String query;

    public QueryCreateTableEvent(int queryId, StackTraceElement[] stackTrace, String query, Statement statement) {
        this.statement = statement;
        this.queryId = queryId;
        this.stackTrace = stackTrace;
        this.query = query;
    }

    public int getQueryId() {
        return queryId;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public String getQuery() {
        return query;
    }

    public Statement getStatement() {
        return statement;
    }
    /**
     * HandlerList for registering events
     *
     * @return HandlerList handler list
     */
    public static HandlerList<QueryCreateTableEvent> getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean value) {
        this.cancel = value;
    }
}
