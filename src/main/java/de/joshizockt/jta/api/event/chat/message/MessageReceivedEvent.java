package de.joshizockt.jta.api.event.chat.message;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.Event;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.object.chat.GenericChat;

public class MessageReceivedEvent implements Event {

    private final JTA jta;

    private final User sender;
    private final GenericChat chat;
    private final Message message;

    public MessageReceivedEvent(JTA jta, Message message, User sender, GenericChat chat) {
        this.jta = jta;
        this.message = message;
        this.sender = sender;
        this.chat = chat;
    }

    @Override
    public JTA getJTA() {
        return jta;
    }

    /**
     * Gets the Sender of the Message as User Instance
     * @return the sender of the Message, is the same as {@link #getMessage()}.getSender()
     */
    public User getSender() {
        return sender;
    }

    /**
     * Gets the Chat the Message was sent in
     * The Chat can be of different Types
     * @return the Chat the Message was sent in, is the same as {@link #getMessage()}.getChat()
     */
    public GenericChat getChat() {
        return chat;
    }

    /**
     * Gets the Message which was sent
     * @return the Message which was sent
     */
    public Message getMessage() {
        return message;
    }

}
