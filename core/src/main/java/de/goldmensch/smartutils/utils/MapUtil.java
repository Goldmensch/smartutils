package de.goldmensch.smartutils.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public static Map<String, String> lowerKeys(Map<String, String> map) {
        Map<String, String> lowerMap = new HashMap<>();
        for(Map.Entry<String, String> c : map.entrySet()) {
            String key = c.getKey().toLowerCase();
            lowerMap.put(key, c.getValue());
        }
        return lowerMap;
    }

}
