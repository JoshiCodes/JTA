package de.joshizockt.jta.api.object;

import de.joshizockt.jta.api.event.Event;

public interface IUpdate<T extends Event> {

    int getId();
    T getUpdate();

}
