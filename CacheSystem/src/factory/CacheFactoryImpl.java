package factory;

import caches.Cache;
import caches.impl.L1Cache;
import caches.impl.L2Cache;
import caches.impl.L3Cache;
import caches.impl.L4Cache;
import model.CacheType;
import model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CacheFactoryImpl implements CacheFactory<String, Event> {

    private final List<Cache<String, Event>> cacheList;

    public CacheFactoryImpl() {
        cacheList = new ArrayList<>();

        Cache<String, Event> fourthCache = new L4Cache();
        fourthCache.setPredecessorCache(null);

        Cache<String, Event> thirdCache = new L3Cache();
        thirdCache.setPredecessorCache(fourthCache);
        fourthCache.setSuccessorCache(thirdCache);

        Cache<String, Event> secondCache = new L2Cache();
        secondCache.setPredecessorCache(thirdCache);
        thirdCache.setSuccessorCache(secondCache);

        Cache<String, Event> firstCache = new L1Cache();
        firstCache.setPredecessorCache(secondCache);
        secondCache.setSuccessorCache(firstCache);

        cacheList.add(fourthCache);
        cacheList.add(thirdCache);
        cacheList.add(secondCache);
        cacheList.add(firstCache);
    }

    // Insert into L4
    @Override
    public void insertValue(String key, Event value) {
        Cache<String, Event> l4Cache = cacheList.get(0);
        l4Cache.insertEvent(key, value);
    }

    @Override
    public void insertValue(String key, Event value, CacheType cacheType) throws Exception {
        Cache<String, Event> cache = cacheList.stream()
                .filter(c -> cacheType.equals(c.getCacheType()))
                .findFirst()
                .orElse(null);

        if (cache == null) {
            throw new Exception("[Factory][Insert] Error retrieving right cache-level");
        }

        cache.insertEvent(key, value);
    }

    // Fetch from L1 cache
    @Override
    public Event fetchValue(String key) {
        Cache<String, Event> l1Cache = cacheList.get(cacheList.size() - 1);
        return l1Cache.fetchEvent(key);
    }

    @Override
    public Event fetchValue(String key, CacheType cacheType) throws Exception {
        Cache<String, Event> cache = cacheList.stream()
                .filter(c -> cacheType.equals(c.getCacheType()))
                .findFirst()
                .orElse(null);

        if (cache == null) {
            throw new Exception("[Factory][Fetch] Error retrieving right cache-level");
        }

        return cache.fetchEvent(key);
    }
}
