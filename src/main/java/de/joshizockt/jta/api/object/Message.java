package de.joshizockt.jta.api.object;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.GenericChat;
import de.joshizockt.jta.api.requests.EditMessageTextRequest;
import de.joshizockt.jta.api.requests.send.SendMessageRequest;
import de.joshizockt.jta.api.rest.RestAction;
import de.joshizockt.jta.api.rest.SendMessageAction;
import de.joshizockt.jta.api.util.JsonUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class Message {

    public static Message fromJson(JTA jta, JsonObject result) {
        if(result == null) return null;
        final int messageId = result.get("message_id").getAsInt();
        final int threadId = JsonUtil.getOrDefaultInt(result, "message_thread_id", 0);
        final User sender = User.fromJson(jta, JsonUtil.getOrDefaultObject(result, "from", null));
        final Date date = new Date(result.get("date").getAsLong() * 1000);
        final int chatId = result.get("chat").getAsJsonObject().get("id").getAsInt();
        final RestAction<GenericChat> chat = jta.getChat(chatId);
        final String text = JsonUtil.getOrDefaultString(result, "text", null);
        if(text == null) return null;
        return new Message() {
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
            public String getContent() {
                return text;
            }

        };
    }

    public abstract JTA getJTA();

    public abstract int getId();
    public abstract int threadId();

    public abstract User getSender();
    public abstract RestAction<GenericChat> getChat();
    public abstract Date date();

    public abstract String getContent();

    @Nullable
    public abstract Message originalMessage();

    public RestAction<Message> editText(String content) {
        return new RestAction<>((v) ->
                getJTA().getRequestHandler().execute(new EditMessageTextRequest(getJTA(), this, content))
        );
    }

    public SendMessageAction reply(String content) {
        return new SendMessageAction(getJTA(), getChat().complete().getId(), content).replyToMessageId(getId());
    }

}
