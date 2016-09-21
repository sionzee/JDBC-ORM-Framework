package cz.ormframework.queries;

import cz.ormframework.annotations.Column;
import cz.ormframework.tools.ColumnEntity;
import cz.ormframework.utils.EntityPair;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class DefaultQueryBase extends QueryBase {
    @Override
    public String createTable(String table, List<ColumnEntity> items) {
        throw new NotImplementedException("QueryBase not found");
    }

    @Override
    public String addIndex(String table, String indexId, Column column) {
        throw new NotImplementedException("QueryBase not found");
    }

    @Override
    public String addConstraint(String table, String id, Column column, String targetEntityTable) {
        throw new NotImplementedException("QueryBase not found");
    }

    @Override
    public String dropTable(String table) {
        throw new NotImplementedException("QueryBase not found");
    }

    @Override
    public String update(String table, Map<String, Object> changes, int id) {
        throw new NotImplementedException("QueryBase not found");
    }

    @Override
    public String insert(String table, Map<String, EntityPair> map) {
        throw new NotImplementedException("QueryBase not found");
    }
}
