package de.joshizockt.jta.api.requests.send;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.message.Message;
import de.joshizockt.jta.api.requests.Request;

public class SendPhotoRequest extends Request<Message> {

    private final JTA jta;

    public SendPhotoRequest(JTA jta, int chatId) {
        super("sendPhoto", RequestMethod.GET);
        this.jta = jta;
        addData("chat_id", chatId);
    }

    public SendPhotoRequest setPhoto(String photo) {
        addData("photo", photo);
        return this;
    }

    @Override
    public Message parse(JsonObject jsonObject) {
        return Message.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
