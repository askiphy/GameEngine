package ru.bananus.gameengine.GamesAPI.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomCasters {
    public static <K,V> HashMap<K,V> setToHashMap(Set<Map.Entry<K,V>> set) {
        HashMap<K,V> mapFromSet = new HashMap<K,V>();
        for(Map.Entry<K,V> entry : set)
        {
            mapFromSet.put(entry.getKey(), entry.getValue());
        }
        return mapFromSet;
    }
}
