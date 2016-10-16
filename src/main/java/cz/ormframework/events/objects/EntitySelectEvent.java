package cz.ormframework.events.objects;

import cz.ormframework.events.Event;
import cz.ormframework.events.HandlerList;
import cz.ormframework.events.interfaces.Cancellable;

import java.util.Collection;

/**
 * Created by siOnzee on 10/16/2016.
 */
public class EntitySelectEvent<EntityType> extends Event implements Cancellable {
    private final static HandlerList<EntityInsertEvent> handlerList = new HandlerList<>();
    private boolean canceled = false;

    private int queryId;
    private StackTraceElement[] stackTraceElements;
    private String query;
    private Collection<EntityType> entities;

    public EntitySelectEvent(int queryId, StackTraceElement[] stackTraceElements, String query, Collection<EntityType> entities) {
        this.queryId = queryId;
        this.stackTraceElements = stackTraceElements;
        this.query = query;
        this.entities = entities;
    }

    public Collection<EntityType> getEntities() {
        return entities;
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


    /**
     * HandlerList for registering events
     *
     * @return HandlerList handler list
     */
    public static HandlerList<EntityInsertEvent> getHandlerList() {
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
