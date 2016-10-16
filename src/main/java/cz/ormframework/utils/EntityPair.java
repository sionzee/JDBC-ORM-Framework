package cz.ormframework.utils;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class EntityPair {
    private boolean entity;
    private Object value;

    public EntityPair(boolean entity, Object value) {
        this.entity = entity;
        this.value = value;
    }

    public boolean isEntity() {
        return entity;
    }

    public Object getValue() {
        return value;
    }
}
