package de.joshizockt.jta.api.requests.send;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.object.User;
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
        if(jsonObject == null || !jsonObject.get("ok").getAsBoolean()) {
            throw new NullPointerException("Response is null or api returned an error");
        }
        if(!jsonObject.has("result")) {
            throw new NullPointerException("The response does not contain a result object");
        }
        return Message.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
