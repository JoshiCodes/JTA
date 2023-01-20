package de.joshizockt.jta.api.object.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.util.JsonUtil;

public abstract class PrivateChat extends GenericChat {

    PrivateChat(JTA jta, long id) {
        super(jta, id);
    }

    public static PrivateChat fromJson(JTA jta, JsonObject object) {
        final int id = object.get("id").getAsInt();
        final String title = JsonUtil.getOrDefaultString(object, "title", null);
        final String firstName = JsonUtil.getOrDefaultString(object, "first_name", null);
        final String lastName = JsonUtil.getOrDefaultString(object, "last_name", null);
        final String username = JsonUtil.getOrDefaultString(object, "username", null);
        final String customEmojiId = JsonUtil.getOrDefaultString(object, "emoji_status_custom_emoji_id", null);
        final String bio = JsonUtil.getOrDefaultString(object, "bio", null);
        final boolean privateForwards = JsonUtil.getBoolean(object, "has_private_forwards");

        return new PrivateChat(jta, id) {

            @Override
            public JTA getJTA() {
                return jta;
            }

            @Override
            public String getFirstName() {
                return firstName;
            }

            @Override
            public String getLastName() {
                return lastName;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String emojiStatusCustomEmojiId() {
                return customEmojiId;
            }

            @Override
            public String getBio() {
                return bio;
            }

            @Override
            public boolean hasPrivateForwards() {
                return privateForwards;
            }

            @Override
            public int getId() {
                return id;
            }

            @Override
            public String getTitle() {
                return title;
            }
        };
    }

    public abstract String getFirstName();
    public abstract String getLastName();
    public abstract String getUsername();
    public abstract String emojiStatusCustomEmojiId();
    public abstract String getBio();

    /**
     * Only of use when getting the Chat from its own Request
     * @return
     */
    public abstract boolean hasPrivateForwards();

}
