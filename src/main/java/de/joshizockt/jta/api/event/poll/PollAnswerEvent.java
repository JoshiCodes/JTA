package de.joshizockt.jta.api.event.poll;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.Event;
import de.joshizockt.jta.api.object.Poll;
import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.rest.RestAction;

public class PollAnswerEvent implements Event {

    private final JTA jta;
    private final String pollId;
    private final User user;
    private final int[] selected;

    public PollAnswerEvent(JTA jta, String pollId, User user, int[] selected) {
        this.jta = jta;
        this.pollId = pollId;
        this.user = user;
        this.selected = selected;
    }

    @Override
    public JTA getJTA() {
        return jta;
    }

    public String getPollId() {
        return pollId;
    }

    public User getUser() {
        return user;
    }

    public int[] getSelected() {
        return selected;
    }

}
