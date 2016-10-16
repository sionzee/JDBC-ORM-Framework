package cz.ormframework.events.objects;

import cz.ormframework.events.Event;
import cz.ormframework.events.HandlerList;
import cz.ormframework.events.interfaces.Cancellable;

import java.sql.Statement;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class QueryFlushEvent extends Event implements Cancellable {
    private final static HandlerList<QueryFlushEvent> handlerList = new HandlerList<>();
    private Statement statement;
    private boolean cancel;

    public QueryFlushEvent(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }
    /**
     * HandlerList for registering events
     *
     * @return HandlerList handler list
     */
    public static HandlerList<QueryFlushEvent> getHandlerList() {
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
