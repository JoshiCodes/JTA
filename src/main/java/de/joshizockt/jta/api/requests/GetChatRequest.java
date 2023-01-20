package de.joshizockt.jta.api.requests;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.object.chat.GenericChat;

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
