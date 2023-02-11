package de.joshizockt.jta.api.requests.send;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.message.Message;
import de.joshizockt.jta.api.requests.Request;

public class SendMessageRequest extends Request<Message> {

    private final JTA jta;

    public SendMessageRequest(JTA jta, String content) {
        super("sendMessage", RequestMethod.POST);
        this.jta = jta;
        addData("text", content);
    }

    public SendMessageRequest(JTA jta, String content, String chatId) {
        this(jta, content);
        addData("chat_id", chatId);
    }

    public SendMessageRequest(JTA jta, String content, int chatId) {
        this(jta, content);
        addData("chat_id", chatId);
    }

    public SendMessageRequest protectContent(boolean protectContent) {
        addData("protect_content", protectContent);
        return this;
    }

    public SendMessageRequest disableWebPagePreview(boolean disablePreview) {
        addData("disable_web_page_preview", disablePreview);
        return this;
    }

    public SendMessageRequest disableNotification(boolean disableNotification) {
        addData("disable_notification", disableNotification);
        return this;
    }

    public SendMessageRequest replyToMessageId(int replyToMessageId) {
        addData("reply_to_message_id", replyToMessageId);
        return this;
    }

    @Override
    public Message parse(JsonObject jsonObject) {
        // Response should be a Message Object
        return Message.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
