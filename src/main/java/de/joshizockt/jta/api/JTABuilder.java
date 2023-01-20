package de.joshizockt.jta.api;

import de.joshizockt.jta.api.event.EventManager;
import de.joshizockt.jta.api.requests.RequestHandler;
import de.joshizockt.jta.api.requests.self.GetSelfRequest;

public class JTABuilder {

    private final String token;

    /**
     * Creates a new JTABuilder Instance
     * @param token
     */
    public JTABuilder(String token) {
        this.token = token;
    }

    public JTA build() {

        RequestHandler requestHandler = new RequestHandler(token);
        JTA jta = new JTA() {

            private EventManager eventHandler = null;

            @Override
            public RequestHandler getRequestHandler() {
                return requestHandler;
            }

            @Override
            public EventManager getEventManager() {
                if(eventHandler == null) {
                    eventHandler = new EventManager(this, 1000L);
                    eventHandler.startFirst();
                }
                return eventHandler;
            }

        };
        jta.getRequestHandler().execute(
                new GetSelfRequest(jta)
        );
        jta.getEventManager();
        return jta;

    }

}
