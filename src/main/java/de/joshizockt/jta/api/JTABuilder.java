package de.joshizockt.jta.api;

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

        RequestHandler handler = new RequestHandler(token);
        JTA jta = new JTA(handler) {};
        jta.getRequestHandler().execute(
                new GetSelfRequest(jta)
        );
        return jta;

    }

}
