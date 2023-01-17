package de.joshizockt.jta.api.object.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.util.JsonUtil;

public abstract class PrivateChat extends GenericChat {

    public static PrivateChat fromJson(JTA jta, JsonObject object) {
        final int id = object.get("id").getAsInt();
        final String title = JsonUtil.getOrDefaultString(object, "title", null);
        final String firstName = JsonUtil.getOrDefaultString(object, "first_name", null);
        final String lastName = JsonUtil.getOrDefaultString(object, "last_name", null);
        final String username = JsonUtil.getOrDefaultString(object, "username", null);
        final String customEmojiId = JsonUtil.getOrDefaultString(object, "emoji_status_custom_emoji_id", null);
        final String bio = JsonUtil.getOrDefaultString(object, "bio", null);
        final boolean privateForwards = JsonUtil.getBoolean(object, "has_private_forwards");

        return new PrivateChat() {

            @Override
            public JTA getJTA() {
                return jta;
            }

            @Override
            public String firstName() {
                return firstName;
            }

            @Override
            public String lastName() {
                return lastName;
            }

            @Override
            public String username() {
                return username;
            }

            @Override
            public String emojiStatusCustomEmojiId() {
                return customEmojiId;
            }

            @Override
            public String bio() {
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
            public String title() {
                return title;
            }
        };
    }

    public abstract String firstName();
    public abstract String lastName();
    public abstract String username();
    public abstract String emojiStatusCustomEmojiId();
    public abstract String bio();

    /**
     * Only of use when getting the Chat from its own Request
     * @return
     */
    public abstract boolean hasPrivateForwards();

}
