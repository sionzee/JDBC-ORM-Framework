package cz.ormframework.parsers;

import cz.ormframework.utils.EntityUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class Parser {

    /**
     * Parse type to the Database type
     *
     * @param type Field Type
     * @return Database Type
     */
    public static String ParseDBType(Class<?> type) {
        String typeName = type.getSimpleName().toLowerCase();
        if(type.isEnum())
            typeName = "enum";

        if(type.isArray() && !ParseDBType(type.getComponentType()).contains("Unsupported DB type")) {
            return "TEXT";
        }

        switch(typeName) {
            case "boolean":
                return "TINYINT";
            case "string":
                return "VARCHAR(255)";
            case "int":
                return "INT(11)";
            case "long":
                return "BIGINT(11)";
            case "byte":
                return "TINYINT";
            case "short":
                return "SMALLINT";
            case "float":
                return "FLOAT";
            case "double":
                return "DOUBLE";
            case "date":
                return "DATE";
            case "timestamp":
                return "TIMESTAMP";
            case "time":
                return "TIME";
            case "year":
                return "YEAR";
            case "char":
                return "VARCHAR(1)";
            case "enum":
                String[] values = new String[type.getEnumConstants().length];
                for(int i = 0; i < type.getEnumConstants().length; i++) values[i] = type.getEnumConstants()[i].toString();
                return "ENUM('" + String.join("', '", (CharSequence[]) values) + "')";
            default:
                if(EntityUtils.isEntity(type)) {
                    return "INT(11)";
                } else {
                    System.out.println("Unsupported DB type: " + type.getSimpleName());
                }
                return "";
        }

    }

    /**
     * Delimiter used for a Arrays
     */
    public static final String DELIMITER = "\u0141\u0141";

    /**
     * Unparse array of type Type
     * @param array Parsed array
     * @param <Type> Return Type
     * @return Return the unparsed array
     */
    private static <Type> Type[] unparseArray(String array) {
        return (Type[]) array.substring(1, array.length() - 1).split(DELIMITER);
    }

    /**
     * Unparse array of type Boolean
     * @param array Parsed array
     * @param <Type> Return Type
     * @return Return the unparsed array
     */
    private static <Type> boolean[] unparseBooleanArray(String array) {
        Object[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        boolean[] arr = new boolean[split.length];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = String.valueOf(split[i]).equalsIgnoreCase("1");
        }
        return arr;
    }

    /**
     * Parse array of type Type
     * @param types Array Instance
     * @param <Type> Return Type
     * @return Return the parsed array
     */
    private static <Type> String parseArray(Type[] types) {
        StringBuilder b = new StringBuilder("[");
        for (Type t : types) {
            b.append(t).append(DELIMITER);
        }
        return b.substring(0, b.length() - DELIMITER.length()) + "]";
    }

    /**
     * Parse array of type Entity
     * @param types Array Instance
     * @param <Type> Return Type
     * @return Return the parsed array
     */
    private static <Type> String parseEntityArray(Type[] types) {
        StringBuilder b = new StringBuilder("[");
        for (Type t : types) {
            int id = EntityUtils.getId(t);
            b.append(id).append(DELIMITER);
        }
        return b.substring(0, b.length() - DELIMITER.length()) + "]";
    }

    /**
     * Parse array of type String
     * @param types Array Instance
     * @param <Type> Return Type
     * @return Return the parsed array
     */
    private static <Type> String parseToStringArray(Type[] types) {
        StringBuilder b = new StringBuilder("[");
        for (Type t : types) {
            b.append(t.toString()).append(DELIMITER);
        }
        return b.substring(0, b.length() - DELIMITER.length()) + "]";
    }

    /**
     * Parse array of type Boolean
     * @param types Array Instance
     * @param <Type> Return Type
     * @return Return the parsed array
     */
    private static <Type> String parseBooleanArray(Type[] types) {
        StringBuilder b = new StringBuilder("[");
        for (Type t : types) {

            b.append(Boolean.valueOf(String.valueOf(t)) ? "1" : "0").append(DELIMITER);
        }
        return b.substring(0, b.length() - DELIMITER.length()) + "]";
    }

    /**
     * Return a array from Object
     * @param val Object which is array
     * @return Array
     */
    private static Object[] getArray(Object val){
        if (val instanceof Object[])
            return (Object[])val;
        int arrlength = Array.getLength(val);
        Object[] outputArray = new Object[arrlength];
        for(int i = 0; i < arrlength; ++i){
            outputArray[i] = Array.get(val, i);
        }
        return outputArray;
    }

    /**
     * Set a field value to the value
     *
     * @param <Type> Type
     * @param value  Value to set
     * @param field  Field where will be value set
     * @param entity Entity as instance
     * @return Entity with set value
     */
    public static <Type> Type FromDBType(Object value, Field field, Type entity) {
        try {
            if(field.getType().isArray()) {
                String type = field.getType().getComponentType().getSimpleName().toLowerCase();
                if(field.getType().getComponentType().isEnum())
                    type = "enum";
                switch (type) {
                    case "boolean":
                        field.set(entity, unparseBooleanArray(String.valueOf(type)));
                        break;
                    case "string":
                        field.set(entity, unparseArray(String.valueOf(value)));
                        break;
                    case "int":
                        field.set(entity, unparseIntArray(String.valueOf(value)));
                        break;
                    case "long":
                        field.set(entity, unparseLongArray(String.valueOf(value)));
                        break;
                    case "byte":
                        field.set(entity, unparseByteArray(String.valueOf(value)));
                        break;
                    case "short":
                        field.set(entity, unparseShortArray(String.valueOf(value)));
                        break;
                    case "float":
                        field.set(entity, unparseFloatArray(String.valueOf(value)));
                        break;
                    case "double":
                        field.set(entity, unparseDoubleArray(String.valueOf(value)));
                        break;
                    case "date":
                        field.set(entity, unparseDateArray(String.valueOf(value)));
                        break;
                    case "timestamp":
                        field.set(entity, unparseTimestampArray(String.valueOf(value)));
                        break;
                    case "time":
                        field.set(entity, unparseTimeArray(String.valueOf(value)));
                        break;
                    case "char":
                        field.set(entity, unparseCharArray(String.valueOf(value)));
                        break;
                    case "enum":
                        String array = String.valueOf(value);

                        List<String> arrayValues = Arrays.asList(array.substring(1, array.length() - 1).split(DELIMITER));
                        Object result = Array.newInstance(field.getType().getComponentType(), arrayValues.size());
                        int index = 0;
                        for(String val : arrayValues) {
                            Array.set(result, index++, Enum.valueOf(field.getType().getComponentType().asSubclass(Enum.class), val));
                        }

                        field.set(entity, result);

                        break;
                }
            } else {
                    String type = field.getType().getSimpleName().toLowerCase();
                if(field.getType().isEnum())
                    type = "enum";
                    switch (type) {
                        case "boolean":
                            field.set(entity, String.valueOf(value).equalsIgnoreCase("1"));
                            break;
                        case "string":
                            field.set(entity, value);
                            break;
                        case "int":
                            field.set(entity, Integer.valueOf(String.valueOf(value)));
                            break;
                        case "long":
                            field.set(entity, Long.valueOf(String.valueOf(value)));
                            break;
                        case "byte":
                            field.set(entity, Byte.valueOf(String.valueOf(value)));
                            break;
                        case "short":
                            field.set(entity, Short.valueOf(String.valueOf(value)));
                            break;
                        case "float":
                            field.set(entity, Float.valueOf(String.valueOf(value)));
                            break;
                        case "double":
                            field.set(entity, Double.valueOf(String.valueOf(value)));
                            break;
                        case "date":
                            field.set(entity, Date.valueOf(String.valueOf(value)));
                            break;
                        case "timestamp":
                            field.set(entity, Timestamp.valueOf(String.valueOf(value)));
                            break;
                        case "time":
                            field.set(entity, Time.valueOf(String.valueOf(value)));
                            break;
                        case "char":
                            field.set(entity, value.toString().charAt(0));
                            break;
                        case "enum":
                            field.set(entity, Enum.valueOf(field.getType().asSubclass(Enum.class), String.valueOf(value)));
                            break;
                    }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return entity;
    }

    /**
     * Unparse array of type Char
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static char[] unparseCharArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        char[] x = new char[split.length];
        for(int i = 0; i < split.length; i++) x[i] = split[i].charAt(0);
        return x;
    }

    /**
     * Unparse array of type Time
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static Time[] unparseTimeArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        Time[] x = new Time[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Time.valueOf(split[i]);
        return x;
    }

    /**
     * Unparse array of type Timestamp
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static Timestamp[] unparseTimestampArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        Timestamp[] x = new Timestamp[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Timestamp.valueOf(split[i]);
        return x;
    }

    /**
     * Unparse array of type Date
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static Date[] unparseDateArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        Date[] x = new Date[split.length];
        for(int i = 0; i < split.length; i++) {
            x[i] = Date.valueOf(split[i]);
        }
        return x;
    }

    /**
     * Unparse array of type Double
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static double[] unparseDoubleArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        double[] x = new double[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Double.valueOf(split[i]);
        return x;
    }

    /**
     * Unparse array of type Float
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static float[] unparseFloatArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        float[] x = new float[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Float.valueOf(split[i]);
        return x;
    }

    /**
     * Unparse array of type Short
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static short[] unparseShortArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        short[] x = new short[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Short.valueOf(split[i]);
        return x;
    }

    /**
     * Unparse array of type Byte
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static byte[] unparseByteArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        byte[] x = new byte[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Byte.valueOf(split[i]);
        return x;
    }

    /**
     * Unparse array of type Long
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static long[] unparseLongArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        long[] x = new long[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Long.valueOf(split[i]);
        return x;
    }

    /**
     * Unparse array of type Int
     * @param array Parsed array
     * @return Return the unparsed array
     */
    private static int[] unparseIntArray(String array) {
        String[] split = array.substring(1, array.length() - 1).split(DELIMITER);
        int[] x = new int[split.length];
        for(int i = 0; i < split.length; i++) x[i] = Integer.valueOf(split[i]);
        return x;
    }

    /**
     * Parse value to the Database type
     *
     * @param field Field
     * @param value Value
     * @return DatabaseValue string
     */
    public static String ToDBType(Field field, Object value) {

        if(field.getType().isArray()) {
            if(EntityUtils.isEntity(field.getType().getComponentType())) {
                return parseEntityArray(getArray(value));
            }
        } else {
            if(EntityUtils.isEntity(field.getType())) {
                return String.valueOf(EntityUtils.getId(value));
            }
        }

        if(field.getType().isArray()) {
            String type = field.getType().getComponentType().getSimpleName().toLowerCase();
            if(field.getType().getComponentType().isEnum())
                type = "enum";

            if(value == null)
                return null;

            Object[] arrayValue = getArray(value);

            switch (type) {
                case "boolean":
                    return parseBooleanArray(arrayValue);
                case "string":
                    return parseArray(arrayValue);
                case "int":
                    return parseArray(arrayValue);
                case "long":
                    return parseArray(arrayValue);
                case "byte":
                    return parseArray(arrayValue);
                case "short":
                    return parseArray(arrayValue);
                case "float":
                    return parseArray(arrayValue);
                case "double":
                    return parseArray(arrayValue);
                case "date":
                    return parseToStringArray(arrayValue);
                case "timestamp":
                    return parseToStringArray(arrayValue);
                case "time":
                    return parseToStringArray(arrayValue);
                case "char":
                    return parseArray(arrayValue);
                case "enum":
                    return parseArray(arrayValue);
            }
        } else {
            String type = field.getType().getSimpleName().toLowerCase();
            if(field.getType().isEnum())
                type = "enum";

            if(value == null)
                return null;

            switch (type) {
                case "boolean":
                    return (boolean) value ? "1" : "0";
                case "string":
                    return String.valueOf(value);
                case "int":
                    return String.valueOf(value);
                case "long":
                    return String.valueOf(value);
                case "byte":
                    return String.valueOf(value);
                case "short":
                    return String.valueOf(value);
                case "float":
                    return String.valueOf(value);
                case "double":
                    return String.valueOf(value);
                case "date":
                    return value.toString();
                case "timestamp":
                    return value.toString();
                case "time":
                    return value.toString();
                case "char":
                    return String.valueOf(value);
                case "enum":
                    return String.valueOf(value);
            }
        }
        return null;
    }

    /**
     * Entity to db type string.
     *
     * @param <Type> the type parameter
     * @param field  the field
     * @param entity the entity
     * @return the string
     */
    public static <Type> String EntityToDBType(Field field, Type entity) {
        try {
            return ToDBType(field, field.get(entity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
