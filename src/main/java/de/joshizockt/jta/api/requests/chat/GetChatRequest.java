package de.joshizockt.jta.api.requests.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.GenericChat;
import de.joshizockt.jta.api.requests.Request;

public class GetChatRequest extends Request<GenericChat> {

    private final JTA jta;

    public GetChatRequest(JTA jta, String id) {
        super("getChat", RequestMethod.GET);
        this.jta = jta;
        addData("chat_id", id);
    }

    @Override
    public GenericChat parse(JsonObject jsonObject) {
        // Response should be a User Object
        return GenericChat.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
