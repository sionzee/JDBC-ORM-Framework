package cz.ormframework.queries;

import cz.ormframework.tools.ColumnEntity;

import java.util.List;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class SQLiteQueryBase extends MySQLQueryBase {
    @Override
    public String createTable(String table, List<ColumnEntity> items) {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS `").append(table).append("` (").append("`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");

        for (ColumnEntity item : items) {
            if(item.getType().startsWith("ENUM("))
                continue;
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
        return query.substring(0, query.length() - ", ".length()) + ")";
    }
}
