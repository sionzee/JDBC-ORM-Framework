package cz.ormframework.repositories;

import cz.ormframework.EntityManager;
import cz.ormframework.annotations.Column;
import cz.ormframework.log.Debug;
import cz.ormframework.parsers.Parser;
import cz.ormframework.utils.EntityUtils;
import cz.ormframework.builder.QueryBuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 *
 * @param <Type> the type parameter
 */
public class Repository<Type> {

    private Class<Type> clazz;
    private EntityManager entityManager;

    /**
     * Instantiates a new Repository.
     *
     * @param clazz         the clazz
     * @param entityManager the entity manager
     */
    public Repository(Class<Type> clazz, EntityManager entityManager) {
        this.clazz = clazz;
        this.entityManager = entityManager;
    }

    /**
     * find query builder . from.
     *
     * @return the query builder . from
     */
    public QueryBuilder<Type>.FROM find() {
        List<String> columns = EntityUtils.getColumns(clazz);
        String[] cols = columns.toArray(new String[columns.size()]);
        return entityManager.getQueryBuilder(clazz).select(cols).from(clazz);
    }

    /**
     * New query query builder.
     *
     * @return the query builder
     */
    public QueryBuilder<Type> newQuery() {
        return entityManager.getQueryBuilder(clazz);
    }

    private Type createType(ResultSet rs) {
        Type type = null;
        try {
            type = clazz.newInstance();

            Type finalType = type;
            Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
                field.setAccessible(true);
                Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
                if (columnAnnotation != null) {
                    String columnName = EntityUtils.getColumnName(field);
                    boolean isEntity = EntityUtils.isEntity((field.getType().isArray() ? field.getType().getComponentType() : field.getType()));
                    try {
                        if(!isEntity)
                            Parser.FromDBType(rs.getObject(columnName), field, finalType);
                        else {
                            if(field.getType().isArray()) {
                                String r = rs.getString(columnName);
                                String[] arrayIds = r.substring(1, r.length() - 1).split(Parser.DELIMITER);
                                Object instances = Array.newInstance(field.getType().getComponentType(), arrayIds.length);
                                for (int i = 0; i < arrayIds.length; i++) {
                                    String s = arrayIds[i];
                                    int id = Integer.parseInt(s);
                                    Array.set(instances, i, entityManager.getRepository(field.getType().getComponentType()).find().where("id = {0}", id).one());
                                }
                                field.set(finalType, instances);
                            } else {
                                field.set(finalType, entityManager.getRepository(field.getType()).find().where("id = {0}", rs.getInt(columnName)).one());
                            }
                        }

                    } catch (SQLException e) {
                        Debug.exception(e);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
                Field entityManagerField = type.getClass().getDeclaredField("entityManager");
                entityManagerField.setAccessible(true);
                entityManagerField.set(type, entityManager);
                entityManagerField.setAccessible(false);
            } catch (NoSuchFieldException ignored) {}

            type = finalType;

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


        return type;
    }

    /**
     * Query collection.
     *
     * @param result the result
     * @return the collection
     */
    public Collection<Type> query(QueryBuilder.RESULT result) {
        List<Type> typeList = new ArrayList<Type>();
        String query = result.toString();
        try {
            Statement st = entityManager.getDatabase().getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
            if(entityManager.getQueryBase().isQueryDelete(query)) {
                st.execute(query);
                return Collections.emptySet();
            }
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                typeList.add(createType(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeList;
    }

    public QueryBuilder.FROM delete() {
        return entityManager.getQueryBuilder(clazz).delete().from(clazz);
    }
}
