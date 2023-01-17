package de.joshizockt.jta.api.object;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.util.JsonUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class Message {

    public static Message fromJson(JTA jta, JsonObject result) {
        final int messageId = result.get("message_id").getAsInt();
        final int threadId = JsonUtil.getOrDefaultInt(result, "message_thread_id", 0);
        final User sender = User.fromJson(jta, result.get("from").getAsJsonObject());
        final Date date = new Date(result.get("date").getAsLong() * 1000);
        //final Chat chat = Chat.fromJson(jta, result.get("chat").getAsJsonObject());
        final Chat chat = null;
        final String text = result.get("text").getAsString();
        return new Message() {
            @Override
            public JTA getJTA() {
                return jta;
            }

            @Override
            public int id() {
                return messageId;
            }

            @Override
            int threadId() {
                return 0;
            }

            @Override
            User sender() {
                return sender;
            }

            @Override
            public Date date() {
                return date;
            }

            @Override
            @Nullable Message originalMessage() {
                return null;
            }

            @Override
            public Chat chat() {
                return chat;
            }

            @Override
            public String text() {
                return text;
            }

        };
    }

    abstract JTA getJTA();

    abstract int id();
    abstract int threadId();

    abstract User sender();
    abstract Chat chat();
    abstract Date date();

    abstract String text();

    @Nullable
    abstract Message originalMessage();

}
