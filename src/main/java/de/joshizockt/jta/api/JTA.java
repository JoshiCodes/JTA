package de.joshizockt.jta.api;

import de.joshizockt.jta.api.event.EventManager;
import de.joshizockt.jta.api.exception.IllegalChatTypeException;
import de.joshizockt.jta.api.object.User;
import de.joshizockt.jta.api.object.chat.GenericChat;
import de.joshizockt.jta.api.object.chat.PrivateChat;
import de.joshizockt.jta.api.requests.GetChatRequest;
import de.joshizockt.jta.api.requests.RequestHandler;
import de.joshizockt.jta.api.requests.self.GetSelfRequest;
import de.joshizockt.jta.api.rest.RestAction;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public abstract class JTA {

    private final Logger logger;

    JTA() {
        this.logger = Logger.getLogger("JTA");
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new JTAFormatter());
        this.logger.addHandler(handler);
        this.logger.setUseParentHandlers(false);
    }

    /**
     * Gets the Logger used by this JTA Instance
     * @return Logger of this JTA Instance
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the RequestHandler of this JTA Instance.
     * The RequestHandler is used to fire and handle requests
     * @return RequestHandler instance of this JTA instance
     */
    public abstract RequestHandler getRequestHandler();

    /**
     * Returns the EventManager of this JTA instance
     * The EventManager is used to fire and receive Events
     * @return EventManager of this JTA instance
     */
    public abstract EventManager getEventManager();

    /**
     * Get the BotUser
     * This returns a normal User Instance
     * @return the User Instance of the Bot
     */
    public RestAction<User> getSelfUser() {
        return new RestAction<>((v) -> getRequestHandler().execute(new GetSelfRequest(this)));
    }

    /**
     * Get a Chat by ID
     * Returns a Chat Instance with any type as GenericChat
     * @param id the ID of the Chat
     * @return the Chat Instance
     */
    public RestAction<GenericChat> getChat(long id) {
        return getChat(String.valueOf(id));
    }

    /**
     * Get a Chat by ID
     * Returns a Chat Instance with any type as GenericChat
     * @param id the ID of the Chat
     * @return the Chat Instance
     */
    public RestAction<GenericChat> getChat(String id) {
        return new RestAction<>((v) -> getRequestHandler().execute(new GetChatRequest(this, id)));
    }

    /**
     * Get a PrivateChat by ID
     * Returns a Chat Instance with the type PrivateChat
     * @param id the ID of the Chat
     * @return the PrivateChat Instance
     * @throws IllegalChatTypeException if the Chat is not a PrivateChat
     */
    public RestAction<PrivateChat> getPrivateChat(long id) {
        return getPrivateChat(String.valueOf(id));
    }

    /**
     * Get a PrivateChat by ID
     * Returns a Chat Instance with the type PrivateChat
     * @param id the ID of the Chat
     * @return the PrivateChat Instance
     * @throws IllegalChatTypeException if the Chat is not a PrivateChat
     */
    public RestAction<PrivateChat> getPrivateChat(String id) {
        return new RestAction<>((v) -> getRequestHandler().execute(new GetChatRequest(this, id)).getAsPrivateChat());
    }

}
