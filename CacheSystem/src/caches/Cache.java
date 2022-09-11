package caches;

import model.CacheType;

public interface Cache<K, V> {

    public void insertEvent(K k, V v);

    public V fetchEvent(K k);

    public void setPredecessorCache(Cache<K, V> cache);

    public void setSuccessorCache(Cache<K, V> cache);

    public CacheType getCacheType();
}
