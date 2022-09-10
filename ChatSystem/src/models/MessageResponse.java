package models;

import java.util.List;

public class MessageResponse {
    private final List<String> message;
    private final int lastPaginatedIndex;

    public MessageResponse(List<String> message, int lastPaginatedIndex) {
        this.message = message;
        this.lastPaginatedIndex = lastPaginatedIndex;
    }

    public List<String> getMessage() {
        return message;
    }

    public int getLastPaginatedIndex() {
        return lastPaginatedIndex;
    }
}
