package cz.ormframework.log;

import com.sun.istack.internal.NotNull;
import cz.ormframework.utils.EntityUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class Debug {
    private static String[] blockedPackages = new String[]{"net.minecraft.server", "org.bukkit", "com.mysql.jdbc", "java.", "sun."};

    /**
     * Exception.
     *
     * @param e the e
     */
    public static void exception(SQLException e) {
        String message = "\n" + Colors.RED + "-------------------EXCEPTION-------------------\n";
        String way = "";
        boolean first = true;
        for (int i = 0; i < e.getStackTrace().length; i++) {
            StackTraceElement ste = e.getStackTrace()[i];
            boolean blocked = false;
            for (String string : blockedPackages) {
                if (ste.getClassName().contains(string)) {
                    blocked = true;
                    break;
                }
            }
            if (blocked) continue;

            way = ste.getMethodName() + " -> " + way;
            if (first) {
                way = way.substring(0, way.length() - 4) + "\n[FIRST-ERROR][" + Colors.GREEN + ste.getClassName() + Colors.RED + "][" + Colors.GREEN + ste.getLineNumber() + Colors.RED + "]";
                first = false;
            } else
                message += "-----------------------------------------------\n";
            message += (ste.getFileName() != null ? "Class: " + ste.getFileName().substring(0, ste.getFileName().length() - 5) : "Class: isn't Accessible") + ", Package: " + ste.getClassName() + "\n"
                    + (ste.getMethodName() != null ? "Method: " + ste.getMethodName() : "Method: isn't Accessible") + "\n"
                    + "Line: " + ste.getLineNumber() + "\n"
                    + "Message: " + e.getMessage() + "\n";
        }

        System.out.println(message + "\n[METHOD-WAY] " + way + Colors.RESET);
    }

    /**
     * Error.
     *
     * @param message the message
     */
    public static void error(String message) {
        System.out.println("\n[" + Colors.RED + "ERROR" + Colors.RESET + "] " + message + Colors.RESET);
    }

    private static Pattern TABLE_OR_COLUMN_PATTERN = Pattern.compile("`(.*?)`");
    private static Pattern VALUE_PATTERN = Pattern.compile("'(.*?)'");

    private static String colorizeQuery(String query) {
        query = Colors.LIGHT + Colors.WHITE + query;

        Matcher match = TABLE_OR_COLUMN_PATTERN.matcher(query);
        while (match.find())
            for (int i = 0; i < match.groupCount(); i++) {
                String content = match.group(i);
                query = query.replace(content.substring(1, content.length() - 1), Colors.LIGHT + Colors.CYAN + content.substring(1, content.length() - 1) + Colors.LIGHT + Colors.WHITE);
            }

        match = VALUE_PATTERN.matcher(query);
        while (match.find())
            for (int i = 0; i < match.groupCount(); i++) {
                String content = match.group(i);
                query = query.replace(content.substring(1, content.length() - 1), Colors.CYAN + content.substring(1, content.length() - 1) + Colors.LIGHT + Colors.WHITE);
            }
        return query;
    }

    /**
     * Query.
     *
     * @param result the result
     */
    public static void query(String result) {
        System.out.println(colorizeQuery(result) + Colors.RESET);
    }

    /**
     * Info.
     *
     * @param message the message
     */
    public static void info(String message) {
        System.out.println(message + Colors.RESET);
    }

    private static String getEntityInfo(@NotNull Object entity, String name, int tabNumber, boolean printInnerEntities) {

        if (entity == null)
            return "null";

        String table = EntityUtils.getTable(entity.getClass());
        if (table == null) {
            return "Isn't Entity";
        }

        StringBuilder s;

        if (name == null) {
            s = new StringBuilder("\n@Table: ").append(Colors.LIGHT + Colors.CYAN).append(table).append(Colors.RESET).append(":\n");
        } else {
            s = new StringBuilder("\nValue of Entity ").append(Colors.LIGHT + Colors.CYAN).append(name).append(Colors.RESET).append(":\n");
        }

        Arrays.stream(entity.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException ignored) {
            }

            if (value != null && EntityUtils.getTable(field.getType()) != null && printInnerEntities) {
                s.append("[Column: ").append(Colors.LIGHT + Colors.CYAN).append(EntityUtils.getColumn(field)).append(Colors.RESET).append(", Type: ").append(EntityUtils.getDBType(field)).append(", ").append("Length: ").append(EntityUtils.getColumn(field).length()).append(", nullable: ").append(EntityUtils.getColumn(field).nullable()).append(", ");
                s.append(field.getName()).append(": ").append(Colors.CYAN).append(value.getClass().getSimpleName()).append("@").append(Integer.toHexString(value.hashCode()));
                s.append(Colors.RESET).append("]\n");
                s.append(getEntityInfo(value, value.getClass().getSimpleName() + "@" + Integer.toHexString(value.hashCode()), tabNumber + 1, true));
                s.append("\n");
            } else {
                if (name != null)
                    for (int i = 0; i < tabNumber; i++)
                        s.append("\t");
                s.append("[Column: ").append(Colors.LIGHT + Colors.CYAN).append(EntityUtils.getColumn(field)).append(Colors.RESET).append(", Type: ").append(EntityUtils.getDBType(field)).append(", ").append("Length: ").append(EntityUtils.getColumn(field).length()).append(", nullable: ").append(EntityUtils.getColumn(field).nullable()).append(", ");

                s.append(field.getName()).append(": ").append(Colors.CYAN).append(value).append(Colors.RESET).append("]\n");
            }
        });
        return s.toString();
    }

    /**
     * Print info about Entity
     *
     * @param entity             Entity which will be printed
     * @param printInnerEntities print entities inside a entity?
     */
    public static void entity(Object entity, boolean printInnerEntities) {
        if (entity == null) {
            info("Null");
            return;
        }

        info(getEntityInfo(entity, null, 0, printInnerEntities));
    }

    /**
     * Warning.
     *
     * @param message the message
     */
    public static void warning(String message) {
        System.out.println(Colors.YELLOW + "[WARNING] " + message + Colors.RESET);
    }
}
