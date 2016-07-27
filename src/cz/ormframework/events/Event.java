package cz.ormframework.events;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public abstract class Event {
    /**
     * Gets handlers.
     *
     * @return the handlers
     */
    public abstract HandlerList getHandlers();
}
