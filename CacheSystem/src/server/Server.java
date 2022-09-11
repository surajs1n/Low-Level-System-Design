package server;

import factory.CacheFactory;
import factory.CacheFactoryImpl;
import model.CacheType;
import model.Event;

public class Server {

    public static void main(String []args) throws Exception {
        CacheFactory<String, Event> cacheFactory = new CacheFactoryImpl();

        cacheFactory.insertValue("k1", new Event("k1", "value1"));
        cacheFactory.insertValue("k2", new Event("k2", "value2"));

        if (cacheFactory.fetchValue("k1") != null) {
            System.out.println(cacheFactory.fetchValue("k1").getValue());
        }

        cacheFactory.insertValue("k3", new Event("k3", "value3"), CacheType.L3);
        if (cacheFactory.fetchValue("k3") != null) {
            System.out.println(cacheFactory.fetchValue("k3").getValue());
        }

        if (cacheFactory.fetchValue("k3") != null) {
            System.out.println(cacheFactory.fetchValue("k3").getValue());
        }

        if (cacheFactory.fetchValue("k3", CacheType.L4) != null) {
            System.out.println(cacheFactory.fetchValue("k1", CacheType.L4).getValue());
        } else {
            System.out.println("[L4Cache] Retrieval fail for key : k3");
        }

        if (cacheFactory.fetchValue("k2", CacheType.L2) != null) {
            System.out.println(cacheFactory.fetchValue("k2", CacheType.L2).getValue());
        } else {
            System.out.println("[L2Cache] Retrieval fail for key : k2");
        }

        if (cacheFactory.fetchValue("k2") != null) {
            System.out.println(cacheFactory.fetchValue("k2").getValue());
        } else {
            System.out.println("[L1Cache] Retrieval fail for key : k2");
        }
    }
}
