Cache System
---

Functional requirement:
1. The cache system would maintain multiple level of caches L1, L2, L3, L4. (minor: levels are extensible)
2. Every cache supports `fetchValue` and `insertValue`
3. `fetchValue` call would work as follows:
   1. First, it will fetch from L1 cache. if it fails to fetch, then it will call L2 cache to fetch the same event.
   2. It will fetch from L2 cache. if it fails, then it will call L3 cache.
   3. It will fetch from L3 cache. if it fails, then it will call L4 cache.
   4. It will fetch from L4 cache, if it fails, then throw exception.
4. `insertValue` call would work as follows:
   1. The event would be directly ingested into the cache until the event volume is lesser than MAX_CACHE_SIZE. After that, one older event would be removed to make space for the new event.
   2. First, it will insert to value into L4 cache.
   3. if the insertValue also have in CacheType into its parameter, then it will store the event into the respective cache.