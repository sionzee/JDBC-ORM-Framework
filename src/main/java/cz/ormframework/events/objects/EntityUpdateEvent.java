package cz.ormframework.events.objects;

import cz.ormframework.events.Event;
import cz.ormframework.events.HandlerList;
import cz.ormframework.events.interfaces.Cancellable;

import java.sql.Statement;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class EntityUpdateEvent<EntityType> extends Event implements Cancellable {
    private final static HandlerList<EntityUpdateEvent> handlerList = new HandlerList<>();
    private boolean canceled = false;

    private int queryId;
    private StackTraceElement[] stackTraceElements;
    private String query;
    private Statement statement;
    private EntityType updateFrom, updateTo;

    public EntityUpdateEvent(int queryId, StackTraceElement[] stackTraceElements, String query, Statement statement, EntityType databaseEntity, EntityType entity) {
        this.queryId = queryId;
        this.stackTraceElements = stackTraceElements;
        this.query = query;
        this.statement = statement;
        this.updateFrom = databaseEntity;
        this.updateTo = entity;
    }

    public EntityType getUpdateFrom() {
        return updateFrom;
    }

    public EntityType getUpdateTo() {
        return updateTo;
    }

    public int getQueryId() {
        return queryId;
    }

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
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
    public static HandlerList<EntityUpdateEvent> getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean value) {
        this.canceled = value;
    }
}
