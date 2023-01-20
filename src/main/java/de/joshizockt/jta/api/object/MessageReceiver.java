package de.joshizockt.jta.api.object;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.requests.send.SendMessageRequest;
import de.joshizockt.jta.api.rest.RestAction;

/**
 * Interface used for all objects that can be messaged
 */
public abstract class MessageReceiver {

    private final JTA jta;
    private final String id;

    public MessageReceiver(JTA jta, long id) {
        this.jta = jta;
        this.id = id + "";
    }

    public MessageReceiver(JTA jta, String id) {
        this.jta = jta;
        this.id = id;
    }

    public RestAction<Message> sendMessage(final String message) {
        SendMessageRequest request = new SendMessageRequest(jta, message, id);
        return new RestAction<>(v -> jta.getRequestHandler().execute(request));
    }

}
