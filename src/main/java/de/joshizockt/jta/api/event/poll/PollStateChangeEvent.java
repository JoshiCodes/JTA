package de.joshizockt.jta.api.event.poll;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.Event;
import de.joshizockt.jta.api.object.Poll;

public class PollStateChangeEvent implements Event {

    private final JTA jta;
    private final Poll poll;

    public PollStateChangeEvent(JTA jta, Poll poll) {
        this.jta = jta;
        this.poll = poll;
    }

    @Override
    public JTA getJTA() {
        return jta;
    }

    public Poll getPoll() {
        return poll;
    }

}
