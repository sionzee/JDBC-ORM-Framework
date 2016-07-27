package cz.ormframework.plugins.bukkit.listeners;

import cz.ormframework.log.Colors;
import cz.ormframework.log.Debug;
import cz.ormframework.utils.Timer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class MainListener implements Listener {


    /**
     * On query done.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onQueryDone(cz.ormframework.plugins.bukkit.events.QueryDoneEvent event) {
        Timer.end(event.getQueryId());
        long number = Timer.get(event.getQueryId());
        long mili;
        Debug.query(Colors.LIGHT + Colors.WHITE + "[Query] " + event.getQuery() + Colors.RESET + Colors.WHITE + " took " + number + "ns (" + (mili = number / 1000) + "ms) (" + (mili / 1000) + "s)" + Colors.RESET);
    }

    /**
     * On execute query.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onExecuteQuery(cz.ormframework.plugins.bukkit.events.ExecuteQueryEvent event) {
        Timer.start(event.getQueryId());
    }
}
