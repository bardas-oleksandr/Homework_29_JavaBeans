package ua.levelup;

import java.util.EventObject;

public class MessageChangeEvent extends EventObject{
    private String oldMessage;
    private String newMessage;

    public MessageChangeEvent(Object source, String oldMessage, String newMessage) {
        super(source);
        this.oldMessage = oldMessage;
        this.newMessage = newMessage;
    }
}
