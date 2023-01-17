package de.joshizockt.jta.api.object;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.requests.send.SendMessageRequest;
import de.joshizockt.jta.api.util.JsonUtil;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class User {

    public static User fromJson(JTA jta, JsonObject jsonObject) {
        final int id = jsonObject.get("id").getAsInt();
        final boolean isBot = jsonObject.get("is_bot").getAsBoolean();
        final String firstName = jsonObject.get("first_name").getAsString();
        final String lastName = JsonUtil.getOrDefaultString(jsonObject, "last_name", null);
        final String username = JsonUtil.getOrDefaultString(jsonObject, "username", null);
        final String languageCode = JsonUtil.getOrDefaultString(jsonObject, "language_code", null);
        return new User() {

            @Override
            JTA getJTA() {
                return jta;
            }

            @Override
            public int id() {
                return id;
            }

            @Override
            public boolean isBot() {
                return isBot;
            }

            @Override
            public String firstName() {
                return firstName;
            }

            @Override
            public @Nullable String lastName() {
                return lastName;
            }

            @Override
            public @Nullable String username() {
                return username;
            }

            @Override
            public @Nullable String languageCode() {
                return languageCode;
            }

        };
    }

    abstract JTA getJTA();

    abstract int id();
    abstract boolean isBot();
    abstract String firstName();
    @Nullable
    abstract String lastName();
    @Nullable
    abstract String username();
    @Nullable
    abstract String languageCode();


    public Message sendMessage(final String message) {
        SendMessageRequest request = new SendMessageRequest(getJTA(), message, id());
        return getJTA().getRequestHandler().execute(request);
    }

}
