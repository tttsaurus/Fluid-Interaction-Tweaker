package com.tttsaurus.fluidintetweaker.common.api.util;

import java.util.HashMap;

public class CachedContainsKeyMap<K, V> extends HashMap<K, V>
{
    private Object cachedKey = null;
    private V cachedValue = null;
    private boolean cachedKeyExists = false;

    @Override
    public boolean containsKey(Object key)
    {
        if (key.equals(cachedKey)) return cachedKeyExists;

        cachedKey = key;
        cachedValue = super.get(key);
        cachedKeyExists = cachedValue != null;

        return cachedKeyExists;
    }

    @Override
    public V get(Object key)
    {
        if (key.equals(cachedKey)) return cachedValue;
        return super.get(key);
    }

    @Override
    public V put(K key, V value)
    {
        if (key.equals(cachedKey)) cachedKey = null;
        return super.put(key, value);
    }

    @Override
    public V remove(Object key)
    {
        if (key.equals(cachedKey)) cachedKey = null;
        return super.remove(key);
    }

    @Override
    public void clear()
    {
        cachedKey = null;
        super.clear();
    }
}
