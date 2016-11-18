package cz.ormframework.utils;

import cz.ormframework.annotations.Column;
import cz.ormframework.annotations.Table;
import cz.ormframework.log.Debug;
import cz.ormframework.parsers.Parser;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class EntityUtils {

    /**
     * Is indexed boolean.
     *
     * @param field the field
     * @return the boolean
     */
    public static boolean isIndexed(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column == null || column.indexed();
    }

    /**
     * Is entity boolean.
     *
     * @param clazz the clazz
     * @return the boolean
     */
    public static boolean isEntity(Class<?> clazz) {
        return clazz.getDeclaredAnnotationsByType(Table.class).length > 0;
    }

    /**
     * Gets db type.
     *
     * @param field the field
     * @return the db type
     */
    public static String getDBType(Field field) {
        Column column = getColumn(field);
        if(column.type().length() > 0)
            return column.type();
        return Parser.ParseDBType(field.getType());
    }

    /**
     * Return a table name by static method getTable()
     */
    private static String getTableNameFromStaticMethod(Class<?> clazz) {
        try {
            Method m = clazz.getMethod("getTable");
            m.setAccessible(true);
            return m.invoke(null).toString();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {}
        return null;
    }

    /**
     * Return a Table from class of entity
     *
     * @param clazz the clazz
     * @return the table
     */
    public static String getTable(@NotNull Class<?> clazz) {
        Table table = clazz.getDeclaredAnnotation(Table.class);
        if (table == null)
            return null;

        String byMethod = getTableNameFromStaticMethod(clazz);
        if(byMethod != null)
            return byMethod;

        if (!table.value().isEmpty())
            return table.value();

        return clazz.getSimpleName();
    }

    /**
     * Set a private ID of instance
     *
     * @param instance the instance
     * @param id       the id
     */
    public static void setId(@NotNull Object instance, int id) {
        if (instance == null)
            return;
        try {
            Field field = instance.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(instance, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Debug.info(Arrays.toString(instance.getClass().getDeclaredFields()));
            e.printStackTrace();
        }

    }

    /**
     * Return a ID from entity
     *
     * @param instance the instance
     * @return the id
     */
    public static int getId(@NotNull Object instance) {
        if (instance == null)
            return -1;
        try {
            Field field = instance.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return field.getInt(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Debug.error("Entity \"" + instance.getClass().getSimpleName() + "\" don't have ID!");
        }
        return -1;
    }

    /**
     * Return Column
     *
     * @param field the field
     * @return the column name
     */
    public static String getColumnName(@NotNull Field field) {
        Column column = getColumn(field);
        if (column == null)
            return null;
        if (!column.value().isEmpty())
            return column.value();
        return field.getName().toLowerCase();
    }

    /**
     * Return Column
     *
     * @param field the field
     * @return the column
     */
    public static Column getColumn(@NotNull Field field) {
        return field.getDeclaredAnnotation(Column.class);
    }


    /**
     * Gets columns.
     *
     * @param <EntityType> the type parameter
     * @param clazz        the clazz
     * @return the columns
     */
    public static <EntityType> List<String> getColumns(Class<EntityType> clazz) {
        List<String> columns = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            String columnName = getColumnName(field);
            if(columnName != null)
                columns.add(columnName);
        });
        return columns;
    }

    /**
     * Compare two arrays boolean.
     *
     * @param array1 the array 1
     * @param array2 the array 2
     * @return the boolean
     */
    public static boolean compareTwoArrays(Object array1, Object array2) {
        if(!array1.getClass().isArray() || !array2.getClass().isArray())
            return false;

        int size = Array.getLength(array1);
        int size1 = Array.getLength(array2);

        if(size != size1)
            return false;

        for(int i = 0; i < size; i++) {
            if(!Array.get(array1, i).equals(Array.get(array2, i)))
                return false;
        }
        return true;
    }

    /**
     * Compare two fields boolean.
     *
     * @param field     the field
     * @param instance  the instance
     * @param instance2 the instance 2
     * @return the boolean
     */
    public static boolean compareTwoFields(@NotNull Field field, @NotNull Object instance, @NotNull Object instance2) {
        field.setAccessible(true);
        try {
            Object val = field.get(instance);
            Object val2 = field.get(instance2);
            field.setAccessible(false);
            if(val == null && val2 == null)
                return true;

            if(val == null || val2 == null)
                return false;

            if(val.getClass().isArray() && val2.getClass().isArray())
                return compareTwoArrays(val, val2);

            if(val.equals(val2))
                return true;

        } catch (IllegalAccessException ignored) {}
        return false;
    }
}
