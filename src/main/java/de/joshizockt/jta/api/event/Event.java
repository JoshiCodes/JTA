package de.joshizockt.jta.api.event;

import de.joshizockt.jta.api.JTA;

public interface Event {

    /**
     * Returns the JTA Instance which received and fired the Event
     * @return the JTA Instance
     */
    JTA getJTA();

}
