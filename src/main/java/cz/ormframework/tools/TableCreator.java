package cz.ormframework.tools;

import cz.ormframework.EntityManager;
import cz.ormframework.annotations.Column;
import cz.ormframework.events.EventManager;
import cz.ormframework.events.objects.QueryCreateTableEvent;
import cz.ormframework.events.objects.QueryDoneEvent;
import cz.ormframework.log.Debug;
import cz.ormframework.utils.EntityUtils;
import cz.ormframework.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class TableCreator {

    private List<Class<?>> tables;
    private EntityManager entityManager;

    /**
     * Instantiates a new Table creator.
     *
     * @param entityManager the entity manager
     */
    public TableCreator(EntityManager entityManager) {
        this.entityManager = entityManager;
        tables = new ArrayList<>();
    }


    /**
     * Create table.
     *
     * @param clazz    the clazz
     * @param recreate the recreate
     */
    public void createTable(Class<?> clazz, boolean recreate) {

        String table = EntityUtils.getTable(clazz);
        if (table == null) {
            Debug.error("Class " + clazz.getSimpleName() + " isn't Entity! Missing @Table");
            return;
        }

        if (recreate)
            try {
                String dropTableQuery = entityManager.getQueryBase().dropTable(table);
                entityManager.getDatabase().getConnection().createStatement().executeUpdate(dropTableQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        String query = entityManager.getQueryBase().createTable(table, Stream.of(clazz.getDeclaredFields())
                .filter(field -> field.getDeclaredAnnotationsByType(Column.class).length > 0)
                .filter(field -> EntityUtils.getColumn(field) != null && !EntityUtils.getColumnName(field).equalsIgnoreCase("id"))
                .map(field ->  {
                    String columnName = EntityUtils.getColumnName(field);
                    Column column = EntityUtils.getColumn(field);
                    return new ColumnEntity(columnName, EntityUtils.getDBType(field), column.length(), column.nullable());
                }).collect(Collectors.toList()));

        int queryID = entityManager.getQueryId();

        try {
            executeQuery(queryID, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeQuery(int queryId, String query) throws Exception {

        Statement statement = entityManager.getDatabase().getConnection().createStatement();

        if(entityManager.areEventsEnabled()) {
            QueryCreateTableEvent event = new QueryCreateTableEvent(queryId, Thread.currentThread().getStackTrace(), query, statement);
            EventManager.FireEvent(event);
            if(event.isCancelled())
                return;
        }

        statement.execute(query);
        statement.close();
        if(entityManager.areEventsEnabled()) {
            QueryDoneEvent queryDoneEvent = new QueryDoneEvent(queryId, null, query);
            EventManager.FireEvent(queryDoneEvent);
        }

    }

    /**
     * Create all tables in jar.
     *
     * @param jar      the jar
     * @param recreate the recreate
     */
    public void createAllTablesInJar(File jar, boolean recreate) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jar);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert jarFile != null;
        Collections.list(jarFile.entries()).forEach(jarEntry -> {
            if (jarEntry.getName().endsWith(".class")) {
                String className = jarEntry.getName().replace("/", ".").replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(className);
                    if (EntityUtils.getTable(clazz) != null)
                        tables.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });

        finishTables( recreate);
    }

    /**
     * Gets tables.
     *
     * @return the tables
     */
    public List<Class<?>> getTables() {
        return tables;
    }

    private void executeQuery(String result) {
        int queryID = entityManager.getQueryId();

        try {
            executeQuery(queryID, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finishTables(boolean recreate) {
        tables.forEach(clazz -> createTable(clazz, recreate));
        tables.forEach(clazz -> Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            if (EntityUtils.getTable(field.getType()) != null && EntityUtils.isIndexed(field)) {
                String indexID = createIndexName("`" + EntityUtils.getTable(clazz) + "`.`" + EntityUtils.getColumn(field) + "` ON `" + EntityUtils.getTable(field.getType()) + "`.`id`");
                executeQuery(entityManager.getQueryBase().addIndex(EntityUtils.getTable(clazz), indexID, EntityUtils.getColumn(field)));
            }
        }));
        tables.forEach(clazz -> Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            if (EntityUtils.getTable(field.getType()) != null && EntityUtils.isIndexed(field)) {
                String indexID = "fk_" + createIndexName("`" + EntityUtils.getTable(clazz) + "`.`" + EntityUtils.getColumn(field) + "` ON `" + EntityUtils.getTable(field.getType()) + "`.`id`");
                executeQuery(entityManager.getQueryBase().addConstraint(EntityUtils.getTable(clazz), indexID, EntityUtils.getColumn(field), EntityUtils.getTable(field.getType())));
            }
        }));
    }

    private String createIndexName(String string) {
        String[] part = string.split(" ON ");

        String[] part1 = part[0].split("\\.");
        String[] part2 = part[1].split("\\.");

        String table1 = part1[0].substring(1, part1[0].length() - 1);
        String value1 = part1[1].substring(1, part1[1].length() - 1);

        String table2 = part2[0].substring(1, part2[0].length() - 1);
        String value2 = part2[1].substring(1, part2[1].length() - 1);
        return table1.toLowerCase() + StringUtils.firstUpper(value1) + "_" + table2.toLowerCase() + StringUtils.firstUpper(value2);
    }

}
