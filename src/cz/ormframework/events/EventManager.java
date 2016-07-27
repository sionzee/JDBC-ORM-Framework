package cz.ormframework.events;

import com.sun.istack.internal.NotNull;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class EventManager {
    /**
     * Call event for all listeners
     *
     * @param <Type> class what extends event
     * @param event  which event
     */
    public static <Type extends Event> void FireEvent(@NotNull Type event) {
        event.getHandlers().execute(event);
    }

}
