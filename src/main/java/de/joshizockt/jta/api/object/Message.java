package de.joshizockt.jta.api.object;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.GenericChat;
import de.joshizockt.jta.api.util.JsonUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class Message {

    public static Message fromJson(JTA jta, JsonObject result) {
        final int messageId = result.get("message_id").getAsInt();
        final int threadId = JsonUtil.getOrDefaultInt(result, "message_thread_id", 0);
        final User sender = User.fromJson(jta, result.get("from").getAsJsonObject());
        final Date date = new Date(result.get("date").getAsLong() * 1000);
        final int chatId = result.get("chat").getAsJsonObject().get("id").getAsInt();
        final GenericChat chat = jta.getChat(chatId + "").complete();
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
            public int threadId() {
                return 0;
            }

            @Override
            public User sender() {
                return sender;
            }

            @Override
            public Date date() {
                return date;
            }

            @Override
            public @Nullable Message originalMessage() {
                return null;
            }

            @Override
            public GenericChat chat() {
                return chat;
            }

            @Override
            public String text() {
                return text;
            }

        };
    }

    public abstract JTA getJTA();

    public abstract int id();
    public abstract int threadId();

    public abstract User sender();
    public abstract GenericChat chat();
    public abstract Date date();

    public abstract String text();

    @Nullable
    public abstract Message originalMessage();

}
