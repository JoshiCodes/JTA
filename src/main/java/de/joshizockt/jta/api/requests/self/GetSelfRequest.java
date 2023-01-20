package de.joshizockt.jta.api.requests.self;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.requests.Request;

public class GetSelfRequest extends Request<User> {

    private final JTA jta;

    public GetSelfRequest(JTA jta) {
        super("getMe", RequestMethod.GET);
        this.jta = jta;
    }

    @Override
    public User parse(JsonObject jsonObject) {
        // Response should be a User Object
        return User.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
