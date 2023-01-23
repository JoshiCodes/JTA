package de.joshizockt.jta.api.exception;

import de.joshizockt.jta.api.object.chat.GenericChat;

public class IllegalChatTypeException extends RuntimeException {

    public IllegalChatTypeException(String msg) {
        super(msg);
    }

    public IllegalChatTypeException(GenericChat chat, String type) {
        this("Chat " + chat.getId() + " is not a " + type + " chat. [" + chat.getClass().getSimpleName() + "]");
    }

}
