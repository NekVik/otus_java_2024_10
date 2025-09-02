package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private static final String ACTION_PUT = "PUT";
    private static final String ACTION_REMOVE = "REMOVE";
    private static final String ACTION_GET = "GET";

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private List<HwListener<K, V>> listeners = new ArrayList<>();

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
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void sendNotify(K key, V value, String action) {

        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception ignore) {
                // do nothing
            }
        });
    }
}
