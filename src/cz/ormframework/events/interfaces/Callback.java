package cz.ormframework.events.interfaces;

import cz.ormframework.events.Event;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 *
 * @param <T> the type parameter
 */
public interface Callback<T extends Event> {
    /**
     * Execute.
     *
     * @param event the event
     */
    void execute(T event);
}
