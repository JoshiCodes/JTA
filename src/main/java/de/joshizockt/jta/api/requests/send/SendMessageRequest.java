package de.joshizockt.jta.api.requests.send;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Message;
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

    @Override
    public Message parse(JsonObject jsonObject) {
        // Response should be a Message Object
        return Message.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
