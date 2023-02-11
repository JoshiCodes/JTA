package de.joshizockt.jta.api.rest.send;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.chat.message.DiceMessage;
import de.joshizockt.jta.api.requests.send.SendDiceRequest;
import de.joshizockt.jta.api.rest.RestAction;

public class SendDiceAction extends RestAction<DiceMessage> {

    private final JTA jta;

    private int chatId;
    private int messageThreadId;
    private boolean disableNotification;
    private boolean protectContent;
    private int replyToMessageId;
    private boolean allowSendingWithoutReply;
    private String emoji;

    public SendDiceAction(JTA jta) {
        super(null);
        this.jta = jta;
    }

    public SendDiceAction setChatId(int chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendDiceAction setMessageThreadId(int messageThreadId) {
        this.messageThreadId = messageThreadId;
        return this;
    }

    public SendDiceAction setDisableNotification(boolean disableNotification) {
        this.disableNotification = disableNotification;
        return this;
    }

    public SendDiceAction setProtectContent(boolean protectContent) {
        this.protectContent = protectContent;
        return this;
    }

    public SendDiceAction setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public SendDiceAction setAllowSendingWithoutReply(boolean allowSendingWithoutReply) {
        this.allowSendingWithoutReply = allowSendingWithoutReply;
        return this;
    }

    /**
     * (Optional)
     * Emoji on which the dice throw animation is based.
     * Currently, must be one of “🎲”, “🎯”, “🏀”, “⚽”, “🎳”, or “🎰”.
     * Dice can have values 1-6 for “🎲”, “🎯” and “🎳”, values 1-5 for “🏀” and “⚽”, and values 1-64 for “🎰”.
     * Defaults to “🎲”.
     * @param emoji the Emoji on which the dice throw animation is based.
     * @return this action
     */
    public SendDiceAction setEmoji(String emoji) {
        this.emoji = emoji;
        return this;
    }

    @Override
    public DiceMessage complete() {

        if (chatId == 0) {
            throw new IllegalArgumentException("ChatId cannot be 0");
        }

        SendDiceRequest request = new SendDiceRequest(jta, chatId);
        if (messageThreadId != 0) {
            request.messageThreadId(messageThreadId);
        }
        if (disableNotification) {
            request.disableNotification(true);
        }
        if (protectContent) {
            request.protectContent(true);
        }
        if (replyToMessageId != 0) {
            request.replyToMessageId(replyToMessageId);
        }
        if (allowSendingWithoutReply) {
            request.allowSendingWithoutReply(true);
        }
        request.emoji(emoji);

        return jta.getRequestHandler().execute(request);

    }

}
