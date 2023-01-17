package de.joshizockt.jta.api.object.chat;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.util.JsonUtil;
import org.jetbrains.annotations.Nullable;

public abstract class GenericChat {

    public static GenericChat fromJson(JTA jta, JsonObject object) {
        //System.out.println(object.toString());
        final int id = object.get("id").getAsInt();
        final String title = JsonUtil.getOrDefaultString(object, "title", null);
        final String type = object.get("type").getAsString();
        if(type.equalsIgnoreCase("private")) {
            return PrivateChat.fromJson(jta, object);
        } else if(type.equalsIgnoreCase("group")) {
            return GroupChat.fromJson(jta, object);
        } else if(type.equalsIgnoreCase("channel")) {
            return ChannelChat.fromJson(jta, object);
        } else {
            return new GenericChat() {

                @Override
                public JTA getJTA() {
                    return jta;
                }

                @Override
                public int getId() {
                    return id;
                }

                @Override
                public @Nullable String title() {
                    return title;
                }
            };
        }
    }

    public abstract JTA getJTA();

    public abstract int getId();
    @Nullable
    public abstract String title();


    public PrivateChat getAsPrivateChat() {
        if(this instanceof PrivateChat) {
            return (PrivateChat) this;
        } else {
            throw new IllegalStateException("This chat is not a private chat!");
        }
    }

}
