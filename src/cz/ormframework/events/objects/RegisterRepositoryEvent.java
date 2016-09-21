package cz.ormframework.events.objects;

import cz.ormframework.events.Event;
import cz.ormframework.events.HandlerList;
import cz.ormframework.events.interfaces.Cancellable;
import cz.ormframework.repositories.Repository;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class RegisterRepositoryEvent<Entity, EntityRepository extends Repository<Entity>> extends Event implements Cancellable {
    private final static HandlerList<QueryDoneEvent> handlerList = new HandlerList<>();
    private boolean canceled = false;
    private Class<EntityRepository> repository;
    private Class<Entity> entity;

    public RegisterRepositoryEvent(Class<EntityRepository> repository, Class<Entity> entity) {
        this.repository = repository;
        this.entity = entity;
    }

    public Class<EntityRepository> getRepository() {
        return repository;
    }

    public Class<Entity> getEntity() {
        return entity;
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

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean value) {
        this.canceled = value;
    }

}
