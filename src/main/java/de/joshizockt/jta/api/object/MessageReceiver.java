package de.joshizockt.jta.api.object;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.requests.send.SendMessageRequest;
import de.joshizockt.jta.api.rest.RestAction;
import de.joshizockt.jta.api.rest.SendMessageAction;
import de.joshizockt.jta.api.rest.SendPollAction;

import java.util.Arrays;

/**
 * Interface used for all objects that can be messaged
 */
public abstract class MessageReceiver {

    private final JTA jta;
    private final int id;

    public MessageReceiver(JTA jta, int id) {
        this.jta = jta;
        this.id = id;
    }

    public SendMessageAction sendMessage(final String message) {
        return new SendMessageAction(jta, id, message);
    }

    public SendPollAction sendPoll() {
        return new SendPollAction(jta, id);
    }

    public SendPollAction sendPoll(final String question, final String... options) {
        return new SendPollAction(jta, id).setQuestion(question).setOptions(Arrays.asList(options));
    }

}
