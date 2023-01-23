package de.joshizockt.jta.api.requests.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.requests.Request;
import de.joshizockt.jta.api.util.JsonUtil;

public class LeaveChatRequest extends Request<Boolean> {

    public LeaveChatRequest(int id) {
        super("leaveChat", RequestMethod.GET);
        addData("chat_id", id);
    }

    @Override
    public Boolean parse(JsonObject jsonObject) {
        return JsonUtil.getOrDefaultBoolean(jsonObject, "result", false);
    }

}
