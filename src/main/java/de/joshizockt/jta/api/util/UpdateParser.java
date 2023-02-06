package de.joshizockt.jta.api.util;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.event.chat.message.MessageEditEvent;
import de.joshizockt.jta.api.event.chat.message.MessageReceivedEvent;
import de.joshizockt.jta.api.event.poll.PollAnswerEvent;
import de.joshizockt.jta.api.object.IUpdate;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.object.User;

public class UpdateParser {

    private final JsonObject json;

    public UpdateParser(JsonObject json) {
        this.json = json;
    }

    public IUpdate<?> parse(JTA jta) {
        final int id = JsonUtil.getOrDefaultInt(json, "update_id", -1);
        if(json.has("message")) {
            Message message = Message.fromJson(jta, json.get("message").getAsJsonObject());
            if(message == null) return null;
            // MessageReceivedEvent
            MessageReceivedEvent event = new MessageReceivedEvent(jta, message, message.getSender(), message.getChat());
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
        } else if (json.has("edited_message")) {
            Message edited = Message.fromJson(jta, json.get("edited_message").getAsJsonObject());
            // MessageEditedEvent
            MessageEditEvent event = new MessageEditEvent(jta, edited, edited.getSender());
            return new IUpdate<MessageEditEvent>() {
                @Override
                public int getId() {
                    return id;
                }

                @Override
                public MessageEditEvent getUpdate() {
                    return event;
                }
            };
        } else if(json.has("poll")) {
            // PollEvent
        } else if(json.has("poll_answer")) {
            // PollAnswerEvent
            JsonObject object = json.get("poll_answer").getAsJsonObject();
            String pollId = JsonUtil.getOrDefaultString(object, "poll_id", null);
            User user = User.fromJson(jta, object.get("user").getAsJsonObject());
            int[] optionIds = JsonUtil.getOrDefaultIntArray(object, "option_ids", null);
            return new IUpdate<PollAnswerEvent>() {
                @Override
                public int getId() {
                    return id;
                }

                @Override
                public PollAnswerEvent getUpdate() {
                    return new PollAnswerEvent(jta, pollId, user, optionIds);
                }
            };
        }
        return null;
    }

}
