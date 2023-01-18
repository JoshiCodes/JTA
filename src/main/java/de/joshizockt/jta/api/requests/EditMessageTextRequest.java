package de.joshizockt.jta.api.requests;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Message;

public class EditMessageTextRequest extends Request<Message> {

    private final JTA jta;

    public EditMessageTextRequest(JTA jta, Message message, String content) {
        super("editMessageText", RequestMethod.POST);
        this.jta = jta;
        addData("chat_id", message.getChat().complete().getId());
        addData("message_id", message.getId());
        addData("text", content);
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
