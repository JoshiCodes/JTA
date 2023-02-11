package de.joshizockt.jta.api.object.chat.message;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.object.chat.GenericChat;
import de.joshizockt.jta.api.rest.RestAction;
import de.joshizockt.jta.api.util.JsonUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class DiceMessage extends Message {

    public static DiceMessage fromJson(JTA jta, JsonObject result) {
        if(result == null) return null;
        final int messageId = result.get("message_id").getAsInt();
        final int threadId = JsonUtil.getOrDefaultInt(result, "message_thread_id", 0);
        final User sender = User.fromJson(jta, JsonUtil.getOrDefaultObject(result, "from", null));
        final Date date = new Date(result.get("date").getAsLong() * 1000);
        final int chatId = result.get("chat").getAsJsonObject().get("id").getAsInt();
        final RestAction<GenericChat> chat = jta.getChat(chatId);
        final JsonObject dice = result.get("dice").getAsJsonObject();
        final String emoji = dice.get("emoji").getAsString();
        final int value = dice.get("value").getAsInt();

        return new DiceMessage() {
            @Override
            public JTA getJTA() {
                return jta;
            }

            @Override
            public int getId() {
                return messageId;
            }

            @Override
            public int threadId() {
                return 0;
            }

            @Override
            public User getSender() {
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
            public RestAction<GenericChat> getChat() {
                return chat;
            }

            @Override
            public String getEmoji() {
                return emoji;
            }

            @Override
            public int getEmojiValue() {
                return value;
            }
        };
    }

    abstract public String getEmoji();
    abstract public int getEmojiValue();

    @Override
    public String getContent() {
        return getEmoji();
    }

}
