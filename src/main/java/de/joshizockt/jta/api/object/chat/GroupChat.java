package de.joshizockt.jta.api.object.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;

public abstract class GroupChat extends GenericChat{

    GroupChat(JTA jta, long id) {
        super(jta, id);
    }

    public static GroupChat fromJson(JTA jta, JsonObject object) {
        return null;
    }

}
