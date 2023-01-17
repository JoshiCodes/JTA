package de.joshizockt.jta.api.object.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;

public abstract class ChannelChat extends GenericChat {

    public static ChannelChat fromJson(JTA jta, JsonObject object) {
        return null;
    }

}
