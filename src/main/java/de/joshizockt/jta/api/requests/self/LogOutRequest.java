package de.joshizockt.jta.api.requests.self;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.requests.Request;

public class LogOutRequest extends Request {

    public LogOutRequest() {
        super("logOut", RequestMethod.GET);
    }

    @Override
    public Object parse(JsonObject jsonObject) {
        return null;
    }

}
