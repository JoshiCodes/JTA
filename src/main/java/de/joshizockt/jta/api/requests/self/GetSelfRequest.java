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
        if(jsonObject == null || !jsonObject.get("ok").getAsBoolean()) {
            throw new NullPointerException("Response is null or api returned an error");
        }
        if(!jsonObject.has("result")) {
            throw new NullPointerException("The response does not contain a result object");
        }
        return User.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
