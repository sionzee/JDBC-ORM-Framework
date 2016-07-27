package cz.ormframework.plugins.bukkit;

import cz.ormframework.events.objects.ExecuteQueryEvent;
import cz.ormframework.events.objects.QueryDoneEvent;
import cz.ormframework.plugins.bukkit.listeners.MainListener;
import cz.ormframework.utils.JarUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class BukkitPlugin extends JavaPlugin {

    /**
     * Hide plugin.
     *
     * @param javaPlugin the java plugin
     */
    public static void HidePlugin(JavaPlugin javaPlugin) {
        SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getPluginManager();
        try {
            Field fieldPlugins = pluginManager.getClass().getDeclaredField("plugins");
            fieldPlugins.setAccessible(true);
            List<Plugin> plugins = (List<Plugin>) fieldPlugins.get(pluginManager);
            Iterator<Plugin> it = plugins.iterator();
            while (it.hasNext()) {
                Plugin plugin = it.next();
                if (plugin.getName().equalsIgnoreCase(javaPlugin.getName())) {
                    it.remove();
                    break;
                }
            }
            fieldPlugins.set(pluginManager, plugins);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        ExecuteQueryEvent.getHandlerList().addListener(event -> Bukkit.getPluginManager().callEvent(new cz.ormframework.plugins.bukkit.events.ExecuteQueryEvent(event)));
        QueryDoneEvent.getHandlerList().addListener(event -> Bukkit.getPluginManager().callEvent(new cz.ormframework.plugins.bukkit.events.QueryDoneEvent(event)));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        if (getConfig().contains("hidden") && getConfig().getBoolean("hidden", true)) {
            HidePlugin(this);
        }

        if (getConfig().contains("default-listener") && getConfig().getBoolean("default-listener", false)) {
            Bukkit.getPluginManager().registerEvents(new MainListener(), this);
        }
    }

    /**
     * Gets jar file.
     *
     * @param javaPlugin the java plugin
     * @return the jar file
     */
    public static File getJarFile(JavaPlugin javaPlugin) {
        File jarFile = refactorFile(javaPlugin);
        if (jarFile == null)
            jarFile = JarUtils.getJarFile(javaPlugin);
        if (jarFile == null)
            jarFile = new File("plugins" + File.pathSeparator + javaPlugin.getDataFolder().getName() + File.separator + "jar");
        return jarFile;
    }

    private static File refactorFile(JavaPlugin javaPlugin) {
        Class<?> clazz = javaPlugin.getClass();
        try {
            Field field = clazz.getSuperclass().getDeclaredField("file");
            field.setAccessible(true);
            return (File) field.get(javaPlugin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

