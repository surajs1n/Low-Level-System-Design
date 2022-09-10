package models;

public class StoredMessage {
    private final String message;

    private final String messageId;

    private final String userId;

    private final long receivedTimestamp;

    public StoredMessage(String message, String messageId, String userId, long receivedTimestamp) {
        this.message = message;
        this.messageId = messageId;
        this.userId = userId;
        this.receivedTimestamp = receivedTimestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getUserId() {
        return userId;
    }

    public long getReceivedTimestamp() {
        return receivedTimestamp;
    }
}
