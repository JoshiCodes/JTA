package de.joshizockt.jta.api;

import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.requests.RequestHandler;
import de.joshizockt.jta.api.requests.self.GetSelfRequest;

public abstract class JTA {

    private final RequestHandler requestHandler;

    JTA(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }


    public User getSelfUser() {
        return requestHandler.execute(new GetSelfRequest(this));
    }

}
