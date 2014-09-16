package ru.shelfcatcher.app.model.data;

/**
 * Created by gadfil on 16.09.2014.
 */
public class Message {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
