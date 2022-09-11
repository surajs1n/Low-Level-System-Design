package factory;

import model.CacheType;

public interface CacheFactory<K, V> {

    public void insertValue(K key, V value);

    public void insertValue(K key, V value, CacheType cacheType) throws Exception;

    public V fetchValue(K key);

    public V fetchValue(K key, CacheType cacheType) throws Exception;
}
