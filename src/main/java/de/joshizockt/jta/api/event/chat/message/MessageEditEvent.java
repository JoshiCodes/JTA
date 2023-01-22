package de.joshizockt.jta.api.event.chat.message;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.Event;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.object.chat.GenericChat;
import de.joshizockt.jta.api.rest.RestAction;

public class MessageEditEvent implements Event {

    private final JTA jta;
    private final Message message;
    private final User sender;

    public MessageEditEvent(final JTA jta, final Message message, final User sender) {
        this.jta = jta;
        this.message = message;
        this.sender = sender;
    }

    @Override
    public JTA getJTA() {
        return jta;
    }

    public Message getMessage() {
        return message;
    }

    public String getNewText() {
        return message.getContent();
    }

    public int getMessageId() {
        return message.getId();
    }

    public User getSender() {
        return sender;
    }

    public RestAction<GenericChat> getChat() {
        return message.getChat();
    }

}
