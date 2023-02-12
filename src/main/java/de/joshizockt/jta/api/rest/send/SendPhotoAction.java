package de.joshizockt.jta.api.rest.send;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.InputFile;
import de.joshizockt.jta.api.object.chat.message.Message;
import de.joshizockt.jta.api.requests.Request;
import de.joshizockt.jta.api.requests.send.SendPhotoRequest;
import de.joshizockt.jta.api.rest.RestAction;

import java.io.File;
import java.util.function.Function;

public class SendPhotoAction extends RestAction<Message> {

    private final JTA jta;
    private final int chatId;

    private String externalPhoto;
    private InputFile internalPhoto;

    private String caption;

    public SendPhotoAction(JTA jta, int chatId) {
        super(null);
        this.jta = jta;
        this.chatId = chatId;
    }

    /**
     * Sets an external photo to be sent. Can be a URL or the file_id of a photo that is already on the Telegram servers.
     * If a File to be sent is set, this will be ignored.
     * @param photo The photo to be sent.
     * @return This action.
     */
    public SendPhotoAction setPhoto(String photo) {
        this.externalPhoto = photo;
        return this;
    }

    /**
     * Sets an internal photo to be sent. This overrides any external photo that was set.
     * @param photo The photo to be sent.
     * @return This action.
     */
    public SendPhotoAction setPhoto(InputFile photo) {
        this.internalPhoto = photo;
        return this;
    }

    /**
     * Sets an internal photo to be sent. This overrides any external photo that was set.
     * @param photo The photo to be sent.
     * @return This action.
     */
    public SendPhotoAction setPhoto(File photo) {
       return setPhoto(InputFile.of(photo));
    }

    public SendPhotoAction setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public Message complete() {
        SendPhotoRequest request = new SendPhotoRequest(jta, chatId);
        if(externalPhoto != null && internalPhoto == null)
            request.setPhoto(externalPhoto);

        if(caption != null)
            request.setCaption(caption);

        if(internalPhoto != null) {
            return jta.getRequestHandler().executeWithFile(request, "photo", internalPhoto);
        } else {
            return jta.getRequestHandler().execute(request);
        }

    }

}
