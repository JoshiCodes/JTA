package de.joshizockt.jta.api.object.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.message.Message;
import de.joshizockt.jta.api.util.JsonUtil;
import org.jetbrains.annotations.Nullable;

public abstract class GroupChat extends GenericChat{

    GroupChat(JTA jta, int id) {
        super(jta, id);
    }

    public static GroupChat fromJson(JTA jta, JsonObject object) {
        final int id = object.get("id").getAsInt();
        final String title = JsonUtil.getOrDefaultString(object, "title", null);
        final String description = JsonUtil.getOrDefaultString(object, "description", null);
        final String inviteLink = JsonUtil.getOrDefaultString(object, "invite_link", null);
        final Message pinnedMessage = Message.fromJson(jta, JsonUtil.getOrDefaultObject(object, "pinned_message", null));
        //final ChatPermissions permissions;

        return new GroupChat(jta, id) {

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public String getInviteLink() {
                return inviteLink;
            }

            @Override
            public Message getPinnedMessage() {
                return pinnedMessage;
            }

            @Override
            public JTA getJTA() {
                return jta;
            }

            @Override
            public int getId() {
                return id;
            }

            @Override
            public @Nullable String getTitle() {
                return title;
            }

        };
    }

    abstract public String getDescription();
    abstract public String getInviteLink();
    abstract public Message getPinnedMessage();

}
