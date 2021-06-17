package de.goldmensch.smartutils.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public static Map<String, String> lowerMap(Map<String, String> map) {
        Map<String, String> lowerMap = new HashMap<>();
        for(Map.Entry<String, String> c : map.entrySet()) {
            String key = c.getKey().toLowerCase();
            String value = c.getValue().toLowerCase();

            lowerMap.put(key, value);
        }
        return lowerMap;
    }

}
