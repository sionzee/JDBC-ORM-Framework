package cz.ormframework.utils;

import java.util.Map;

/**
 * Created by siOnzee on 20.09.2016.
 */
public class MapUtils {

    public static <Key, Value> Value getOrDefaultPut(Map<Key, Value> map, Key key, Value value) {
        if(map.containsKey(key))
            return map.get(key);
        map.put(key, value);
        return map.get(key);
    }
}
