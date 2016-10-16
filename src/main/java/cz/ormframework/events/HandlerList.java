package cz.ormframework.events;

import cz.ormframework.events.interfaces.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 *
 * @param <Type> the type parameter
 */
public class HandlerList<Type extends Event> {

    /**
     * List of listeners
     */
    private List<Callback<Type>> listeners = new ArrayList<>();

    /**
     * Add listener to the list
     *
     * @param callback listener
     */
    public void addListener(Callback<Type> callback) {
        listeners.add(callback);
    }

    /**
     * Remove listener from the list
     *
     * @param callback listener
     */
    public void removeListener(Callback<Type> callback) {
        listeners.remove(callback);
    }

    /**
     * Execute on all listeners
     *
     * @param type EventParameter
     */
    void execute(Type type) {
        listeners.forEach(typeCallback -> typeCallback.execute(type));
    }


}
