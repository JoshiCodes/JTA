package de.joshizockt.jta.api.event.jta;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.Event;

public class ReadyEvent implements Event {

    private final JTA jta;

    /**
     * Event which is called when the JTA is ready
     * Will be called after the first update
     * @param jta JTA Instance
     */
    public ReadyEvent(JTA jta) {
        this.jta = jta;
    }

    @Override
    public JTA getJTA() {
        return jta;
    }

}
