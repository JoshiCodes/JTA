package de.joshizockt.jta.api.rest;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.requests.send.SendMessageRequest;

public class SendMessageAction extends RestAction<Message> {

    private final JTA jta;
    private final int id;
    private String content;

    private boolean protectContent = false;
    private boolean disablePreview = false;
    private boolean disableNotification = false;
    private int replyToMessageId = -1;

    public SendMessageAction(final JTA jta, final int id, final String content) {
        super(null);
        this.jta = jta;
        this.id = id;
        this.content = content;
    }

    public SendMessageAction setContent(String content) {
        this.content = content;
        return this;
    }

    public SendMessageAction protectContent(boolean protectContent) {
        this.protectContent = protectContent;
        return this;
    }

    public SendMessageAction disableWebPagePreview(boolean disablePreview) {
        this.disablePreview = disablePreview;
        return this;
    }

    public SendMessageAction disableNotification(boolean disableNotification) {
        this.disableNotification = disableNotification;
        return this;
    }

    public SendMessageAction replyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    @Override
    public Message complete() {
        SendMessageRequest request = new SendMessageRequest(jta, content, id);

        if(protectContent)
            request.protectContent(true);
        if(disablePreview)
            request.disableWebPagePreview(true);
        if(disableNotification)
            request.disableNotification(true);
        if(replyToMessageId != -1)
            request.replyToMessageId(replyToMessageId);

        return jta.getRequestHandler().execute(request);
    }
    
}
