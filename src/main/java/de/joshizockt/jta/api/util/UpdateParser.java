package de.joshizockt.jta.api.util;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.chat.MessageReceivedEvent;
import de.joshizockt.jta.api.object.IUpdate;
import de.joshizockt.jta.api.object.Message;

public class UpdateParser {

    private final JsonObject json;

    public UpdateParser(JsonObject json) {
        this.json = json;
    }

    public IUpdate<?> parse(JTA jta) {
        final int id = JsonUtil.getOrDefaultInt(json, "update_id", -1);
        if(json.has("message")) {
            Message message = Message.fromJson(jta, json.get("message").getAsJsonObject());
            // MessageReceivedEvent
            MessageReceivedEvent event = new MessageReceivedEvent(jta, message, message.getSender(), message.getChat().complete());
            return new IUpdate<MessageReceivedEvent>() {
                @Override
                public int getId() {
                    return id;
                }

                @Override
                public MessageReceivedEvent getUpdate() {
                    return event;
                }
            };
        }
        return null;
    }

}
