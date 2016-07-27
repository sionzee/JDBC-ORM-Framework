package cz.ormframework.events.interfaces;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public interface Cancellable {
    /**
     * Is cancelled boolean.
     *
     * @return the boolean
     */
    boolean isCancelled();

    /**
     * Sets cancelled.
     *
     * @param value the value
     */
    void setCancelled(boolean value);
}
