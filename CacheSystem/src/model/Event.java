package model;

public class Event {
    private final String key;
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Event(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
