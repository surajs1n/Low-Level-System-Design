package caches.impl;

import caches.Cache;
import model.CacheType;
import model.Event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class L2Cache implements Cache<String, Event> {

    private final static int MAX_SIZE = 10;
    private final CacheType cacheType = CacheType.L2;
    private Cache<String, Event> predecessorCache = null;
    private Cache<String, Event> successorCache = null;

    private final Map<String, Event> internalStorage = new ConcurrentHashMap<>();
    private final Queue<Event> internalQueue = new LinkedList<>();

    @Override
    public CacheType getCacheType() {
        return cacheType;
    }

    public Cache<String, Event> getPredecessorCache() {
        return predecessorCache;
    }

    @Override
    public void setPredecessorCache(Cache<String, Event> predecessorCache) {
        this.predecessorCache = predecessorCache;
    }

    public Cache<String, Event> getSuccessorCache() {
        return successorCache;
    }

    @Override
    public void setSuccessorCache(Cache<String, Event> successorCache) {
        this.successorCache = successorCache;
    }

    @Override
    public synchronized void insertEvent(String key, Event event) {
        Event fetchedEvent = internalStorage.get(key);

        if (internalQueue.size() < MAX_SIZE) {
            if (fetchedEvent == null) {
                internalQueue.add(event);
            }

        } else {
            if (fetchedEvent == null) {
                Event olderEvent = internalQueue.poll();
                assert olderEvent != null;
                internalStorage.remove(olderEvent.getKey());
                System.out.println("[L2Cache] Size is almost full, emptying older record : " + olderEvent.getKey());
                internalQueue.add(event);
            }
        }

        internalStorage.put(key, event);
    }

    @Override
    public Event fetchEvent(String key) {
        Event fetchedEvent = internalStorage.get(key);

        if (fetchedEvent == null) {
            System.out.println("[L2Cache] Event doesn't exist for key: " + key + " searching in L3Cache.");
            fetchedEvent = getPredecessorCache().fetchEvent(key);

            if (fetchedEvent == null) {
                System.out.println("[L2Cache] Event doesn't exist in predecessor : " + key);
            } else {
                insertEvent(key, fetchedEvent);
            }

        }

        return fetchedEvent;
    }
}
