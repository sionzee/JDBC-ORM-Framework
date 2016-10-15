package cz.ormframework.builder;

import cz.ormframework.EntityManager;
import cz.ormframework.log.Debug;
import cz.ormframework.utils.*;
import cz.ormframework.utils.exceptions.UnknownValueException;

import java.util.*;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 *
 * @param <Entity> the type parameter
 */
public abstract class QueryBuilder<Entity> {

    private List<String> columns = new ArrayList<>();
    private List<String> reservedWords = Arrays.asList("XOR", "OR", "LIKE", "=", "NOT", "<=", ">=", "BETWEEN", "AND", "IN");
    private Class<Entity> clazz;
    private EntityManager entityManager;
    private String _table;
    /**
     * The Query.
     */
    protected String _query;

    /**
     * Instantiates a new Query builder.
     *
     * @param clazz         the clazz
     * @param entityManager the entity manager
     */
    public QueryBuilder(Class<Entity> clazz, EntityManager entityManager) {
        this.clazz = clazz;
        this.entityManager = entityManager;
        this._table = EntityUtils.getTable(clazz);
        this.columns = EntityUtils.getColumns(clazz);
    }

    /**
     * The enum Ordering.
     */
    public enum ORDERING {
        /**
         * Asc ordering.
         */
        ASC, /**
         * Desc ordering.
         */
        DESC
    }

    private String normalize(String table) {
        return "`" + table + "`";
    }


    /* --------------------------------------- order --------------------------------------- */

    /**
     * The type Order.
     */
    public class ORDER extends RESULT {
        /**
         * Instantiates a new Order.
         *
         * @param query    the query
         * @param column   the column
         * @param ordering the ordering
         */
        public ORDER(String query, String column, ORDERING ordering) {
            _query = query + " order BY " + normalize(column) + " " + ordering.name();
        }
    }

    /* --------------------------------------- RESULT --------------------------------------- */

    /**
     * The type Result.
     */
    public class RESULT {
        /**
         * One entity.
         *
         * @return the entity
         */
        public Entity one() {
            Iterator<Entity> it = entityManager.getRepository(clazz).query(this).iterator();
            if(!it.hasNext())
                return null;
            return it.next(); }

        /**
         * Limit collection.
         *
         * @param from the from
         * @param to   the to
         * @return the collection
         */
        public Collection<Entity> limit(int from, int to) { return entityManager.getRepository(clazz).query(this); }

        /**
         * All collection.
         *
         * @return the collection
         */
        public Collection<Entity> all() {return entityManager.getRepository(clazz).query(this);}

        @Override
        public String toString() {
            return _query;
        }
    }

    /* --------------------------------------- where --------------------------------------- */

    /**
     * The type Where.
     */
    public class WHERE extends RESULT {
        /**
         * Instantiates a new Where.
         *
         * @param query     the query
         * @param condition the condition
         * @param params    the params
         */
        public WHERE(String query, String condition, Object[] params) {
            _query = query + " where ";

            if(params.length > 0)
                for(int i = 0; i < params.length; i++) {
                    if(!condition.contains("{" + i +"}"))
                        Debug.error("Wrong where condition! \"" + condition + "\" missing {" + i + "} (" + params[i] + ")");
                }

            boolean first = true;
            for(String word : condition.split(" ")) {
                if(!reservedWords.contains(word)) {
                    if(columns.contains(word)) {
                        _query += (first ? "":" ") + normalize(_table) + "." +  normalize(word);
                    } else {
                        if(!word.startsWith("'") && !word.endsWith("'"))
                            _query += (first ? "":" ") + "'" + word + "'";
                    }
                } else {
                    _query += (first ? "":" ") + word;
                }
                if(first) first = false;
            }
            _query = cz.ormframework.utils.Formatter.format(_query, params);
        }

        /**
         * Order order.
         *
         * @param column   the column
         * @param ordering the ordering
         * @return the order
         */
        public ORDER order(String column, ORDERING ordering) {
            return new ORDER(_query, column, ordering);
        }
    }

     /* --------------------------------------- from --------------------------------------- */

    /**
     * The type From.
     */
    public class FROM extends RESULT {
        /**
         * Instantiates a new From.
         *
         * @param query   the query
         * @param classes the classes
         */
        public FROM(String query, Class... classes) {
            _query = query + " from ";
            if(classes.length <= 0)
                try {
                    throw new UnknownValueException("[QueryBuilder][from] Can't take from (0 params or null). " + query);
                } catch (UnknownValueException e) {
                    e.printStackTrace();
                }

            for(Class<?> clazz : classes) {
                _query += normalize(EntityUtils.getTable(clazz)) + ", ";
            }
            _query = _query.substring(0, _query.length() - 2);
        }

        /**
         * Where where.
         *
         * @param condition the condition
         * @param params    the params
         * @return the where
         */
        public WHERE where(String condition, Object... params) {
            return new WHERE(_query, condition, params);
        }

        /**
         * Order order.
         *
         * @param column   the column
         * @param ordering the ordering
         * @return the order
         */
        public ORDER order(String column, ORDERING ordering) {
            return new ORDER(_query, column, ordering);
        }
    }

    /* --------------------------------------- SELECT, UPDATE, DELETE --------------------------------------- */


    /**
     * The type Select.
     */
    public class SELECT {
        /**
         * Instantiates a new Select.
         *
         * @param params the params
         */
        public SELECT(String[] params) {
            if(params.length <= 0)
                try {
                    throw new UnknownValueException("[QueryBuilder][select] Can't select from (0 params or null).");
                } catch (UnknownValueException e) {
                    e.printStackTrace();
                }
            _query = "SELECT ";

            for (String table : params) {
                if(table.equalsIgnoreCase("*") || table.endsWith("*"))
                    _query += normalize(_table) + "." + table + ", ";
                else
                    _query += normalize(_table) + "." + normalize(table) + ", ";
            }
            _query = _query.substring(0, _query.length() - 2);
        }

        /**
         * From from.
         *
         * @param classes the classes
         * @return the from
         */
        public FROM from(Class... classes) {
            return new FROM(_query, classes);
        }
    }

    public class DELETE {
        public DELETE() {
            _query = "DELETE ";
        }

        public FROM from(Class... classes) {
            return new FROM(_query, classes);
        }
    }

    /* --------------------------------------- Query Builder --------------------------------------- */

    /**
     * Select select.
     *
     * @param params the params
     * @return the select
     */
    public SELECT select(String... params) {
        return new SELECT(params);
    }

    public DELETE delete() {
        return new DELETE();
    }

}
