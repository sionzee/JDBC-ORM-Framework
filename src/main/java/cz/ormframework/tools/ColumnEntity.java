package cz.ormframework.tools;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class ColumnEntity {
    private String name, type;
    private int length;
    private boolean nullable;

    public ColumnEntity(String name, String type, int length, boolean nullable) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public boolean isNullable() {
        return nullable;
    }
}
