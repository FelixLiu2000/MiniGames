package com.group0611.uoftgame.utilities;

import java.util.Map;

// helper code in this file found at https://stackoverflow.com/questions/49086563/how-to-insert-multiple-keys-with-the-same-value-into-a-hash-map-in-java

public enum HashMapHelper {
    ; // Utility class for working with maps
    public static <K,V> void multiKeyPut(Map<? super K,? super V> map, K[] keys, V value) {
        for(K key : keys) {
            map.put(key, value);
        }
    }
}
