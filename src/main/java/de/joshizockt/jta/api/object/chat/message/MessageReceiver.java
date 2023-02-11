package de.joshizockt.jta.api.object.chat.message;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.InputFile;
import de.joshizockt.jta.api.rest.send.SendDiceAction;
import de.joshizockt.jta.api.rest.send.SendMessageAction;
import de.joshizockt.jta.api.rest.send.SendPhotoAction;
import de.joshizockt.jta.api.rest.send.SendPollAction;

import java.io.File;
import java.util.Arrays;

/**
 * Interface used for all objects that can be messaged
 */
public abstract class MessageReceiver {

    private final JTA jta;
    private final int id;

    public MessageReceiver(JTA jta, int id) {
        this.jta = jta;
        this.id = id;
    }

    public SendMessageAction sendMessage(final String message) {
        return new SendMessageAction(jta, id, message);
    }

    public SendPollAction sendPoll() {
        return new SendPollAction(jta, id);
    }

    public SendPollAction sendPoll(final String question, final String... options) {
        return new SendPollAction(jta, id).setQuestion(question).setOptions(Arrays.asList(options));
    }

    public SendDiceAction sendDice() {
        return new SendDiceAction(jta).setChatId(id);
    }

    public SendDiceAction sendDice(String emoji) {
        return sendDice().setEmoji(emoji);
    }

    /**
     * Sends a local photo to this receiver
     * @param photo the photo to send
     * @return the action to execute
     */
    public SendPhotoAction sendPhoto(File photo) {
        return new SendPhotoAction(jta, id).setPhoto(photo);
    }

    /**
     * Sends a photo to this receiver
     * @param photo the photo to send
     * @return the action to execute
     */
    public SendPhotoAction sendPhoto(InputFile photo) {
        return new SendPhotoAction(jta, id).setPhoto(photo);
    }

    /**
     * Sends a photo to this receiver
     * @param url the url of the photo or the file_id of a photo that is already on Telegram's servers
     * @return the action to execute
     */
    public SendPhotoAction sendPhoto(String url) {
        return new SendPhotoAction(jta, id).setPhoto(url);
    }

}
