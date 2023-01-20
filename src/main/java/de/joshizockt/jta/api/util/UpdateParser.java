package de.joshizockt.jta.api.util;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.event.chat.ChatMessageReceivedEvent;
import de.joshizockt.jta.api.object.IUpdate;

public class UpdateParser {

    private final JsonObject json;

    public UpdateParser(JsonObject json) {
        this.json = json;
    }

    public IUpdate<?> parse() {
        final int id = JsonUtil.getOrDefaultInt(json, "update_id", -1);
        if(json.has("message")) {
            // MessageReceivedEvent
            ChatMessageReceivedEvent event = new ChatMessageReceivedEvent();
            return new IUpdate<ChatMessageReceivedEvent>() {
                @Override
                public int getId() {
                    return id;
                }

                @Override
                public ChatMessageReceivedEvent getUpdate() {
                    return event;
                }
            };
        }
        return null;
    }

}
