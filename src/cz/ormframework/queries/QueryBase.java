package cz.ormframework.queries;

import cz.ormframework.annotations.Column;
import cz.ormframework.tools.ColumnEntity;
import cz.ormframework.utils.EntityPair;

import java.util.List;
import java.util.Map;

/**
 * Created by siOnzee on 21.09.2016.
 */
public abstract class QueryBase {
    public abstract String createTable(String table, List<ColumnEntity> items);
    public abstract String addIndex(String table, String indexId, Column column);
    public abstract String addConstraint(String table, String id, Column column, String targetEntityTable);
    public abstract String dropTable(String table);
    public abstract String update(String table, Map<String, Object> changes, int id);
    public abstract String insert(String table, Map<String, EntityPair> map);
    public abstract boolean isQueryDelete(String query);
}
