package de.joshizockt.jta.api.exception;

import de.joshizockt.jta.api.object.chat.GenericChat;

public class IllegalChatTypeException extends RuntimeException {

    public IllegalChatTypeException(GenericChat chat, String type) {
        super("Chat " + chat.getId() + " is not a " + type + " chat. [" + chat.getClass().getSimpleName() + "]");
    }

}
