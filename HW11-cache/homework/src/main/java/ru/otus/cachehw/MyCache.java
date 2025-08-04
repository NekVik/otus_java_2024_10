package ru.otus.cachehw;

import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private static final String ACTION_PUT = "PUT";
    private static final String ACTION_REMOVE = "REMOVE";
    private static final String ACTION_GET = "GET";

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private HwListener<K, V> listenerInstance;

    @Override
    public void put(K key, V value) {

        cache.put(key, value);

        sendNotify(key, value, ACTION_PUT);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);

        sendNotify(key, null, ACTION_REMOVE);
    }

    @Override
    public V get(K key) {
        var value = cache.get(key);

        sendNotify(key, value, ACTION_GET);

        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerInstance = listener;
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerInstance = null;
    }

    private void sendNotify(K key, V value, String action) {

        if (listenerInstance != null) {
            listenerInstance.notify(key, value, action);
        }

    }

}
