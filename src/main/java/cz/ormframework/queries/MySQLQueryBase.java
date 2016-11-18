package cz.ormframework.queries;

import cz.ormframework.annotations.Column;
import cz.ormframework.tools.ColumnEntity;
import cz.ormframework.utils.EntityPair;
import cz.ormframework.utils.Formatter;

import java.util.List;
import java.util.Map;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class MySQLQueryBase extends QueryBase {

    @Override
    public String createTable(String table, List<ColumnEntity> items) {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS `").append(table).append("` (").append("`id` INT NOT NULL AUTO_INCREMENT, ");

        for (ColumnEntity item : items) {
            query.append("`").append(item.getName()).append("` ").append(item.getType());
            if (item.getLength() > 0)
                query.append("(").append(item.getLength()).append(") ");
            else
                query.append(" ");

            if (item.isNullable())
                query.append("NULL");
            else
                query.append("NOT NULL");

            query.append(", ");
        }
        query.append("PRIMARY KEY(`id`))");
        return query.toString();
    }

    @Override
    public String addIndex(String table, String indexId, Column column) {
        return Formatter.format("ALTER TABLE `{0}` ADD INDEX `{1}` (`{2}`)", table, indexId, column);
    }

    @Override
    public String addConstraint(String table, String id, Column column, String targetEntityTable) {
        return Formatter.format("ALTER TABLE `{0}` ADD CONSTRAINT `{1}` FOREIGN KEY (`{2}`) REFERENCES {3}(`id`) ON UPDATE CASCADE ON DELETE CASCADE", table, id, column, targetEntityTable);
    }

    @Override
    public String dropTable(String table) {
        return Formatter.format("DROP TABLE IF EXISTS `{0}`", table);
    }

    @Override
    public String update(String table, Map<String, Object> changes, int id) {
        StringBuilder query = new StringBuilder();
        changes.forEach((s, o) -> {
            if(o != null) {
                query.append("`").append(s).append("` = '").append(o).append("'").append(", ");
            } else {
                query.append("`").append(s).append("` = NULL").append(", ");
            }
        });

        return Formatter.format("UPDATE `{0}` SET {1} WHERE {2}", table, query.substring(0, query.length() - 2), "`id` = '" + id + "'");
    }

    @Override
    public String insert(String table, Map<String, EntityPair> map) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        map.forEach((s, entityPair) -> {
            if(!entityPair.isEntity() || (entityPair.isEntity() && Integer.valueOf(entityPair.getValue().toString()) > -1)) {
                columns.append("`").append(s).append("`").append(", ");
                if(entityPair.getValue() != null)
                    values.append("'").append(entityPair.getValue()).append("'").append(", ");
                else values.append("null").append(", ");
            }
        });

        return Formatter.format("INSERT INTO `{0}` ({1}) VALUES ({2})", table, columns.substring(0, columns.length() - 2), values.substring(0, values.length() - 2));
    }

    @Override
    public boolean isQueryDelete(String query) {
        return query.toLowerCase().startsWith("delete");
    }

    @Override
    public String delete(String table, int id) {
        return Formatter.format("DELETE FROM `{0}` WHERE `id` = '{1}'", table, id);
    }
}
