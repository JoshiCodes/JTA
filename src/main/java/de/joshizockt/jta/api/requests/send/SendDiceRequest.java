package de.joshizockt.jta.api.requests.send;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.message.DiceMessage;
import de.joshizockt.jta.api.object.chat.message.Message;
import de.joshizockt.jta.api.requests.Request;

public class SendDiceRequest extends Request<DiceMessage> {

    private final JTA jta;

    public SendDiceRequest(JTA jta, int chatId) {
        super("sendDice", RequestMethod.GET);
        this.jta = jta;
        addData("chat_id", chatId);
    }

    public SendDiceRequest messageThreadId(int messageThreadId) {
        addData("message_thread_id", messageThreadId);
        return this;
    }

    public SendDiceRequest disableNotification(boolean disableNotification) {
        addData("disable_notification", disableNotification);
        return this;
    }

    public SendDiceRequest protectContent(boolean protectContent) {
        addData("protect_content", protectContent);
        return this;
    }

    public SendDiceRequest replyToMessageId(int replyToMessageId) {
        addData("reply_to_message_id", replyToMessageId);
        return this;
    }

    public SendDiceRequest allowSendingWithoutReply(boolean allowSendingWithoutReply) {
        addData("allow_sending_without_reply", allowSendingWithoutReply);
        return this;
    }

    /**
     * (Optional)
     * Emoji on which the dice throw animation is based.
     * Currently, must be one of “🎲”, “🎯”, “🏀”, “⚽”, “🎳”, or “🎰”.
     * Dice can have values 1-6 for “🎲”, “🎯” and “🎳”, values 1-5 for “🏀” and “⚽”, and values 1-64 for “🎰”.
     * Defaults to “🎲”.
     * @param emoji the Emoji on which the dice throw animation is based.
     * @return this request
     */
    public SendDiceRequest emoji(String emoji) {
        addData("emoji", emoji);
        return this;
    }

    @Override
    public DiceMessage parse(JsonObject jsonObject) {
        return DiceMessage.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
