package de.joshizockt.jta.api;

import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.object.chat.GenericChat;
import de.joshizockt.jta.api.object.chat.PrivateChat;
import de.joshizockt.jta.api.requests.GetChatRequest;
import de.joshizockt.jta.api.requests.RequestHandler;
import de.joshizockt.jta.api.requests.self.GetSelfRequest;
import de.joshizockt.jta.api.rest.RestAction;

public abstract class JTA {

    private final RequestHandler requestHandler;

    JTA(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }


    public RestAction<User> getSelfUser() {
        return new RestAction<>((v) -> requestHandler.execute(new GetSelfRequest(this)));
    }

    public RestAction<GenericChat> getChat(String id) {
        return new RestAction<>((v) -> requestHandler.execute(new GetChatRequest(this, id)));
    }

    public RestAction<PrivateChat> getPrivateChat(String id) {
        return new RestAction<>((v) -> requestHandler.execute(new GetChatRequest(this, id)).getAsPrivateChat());
    }

}
