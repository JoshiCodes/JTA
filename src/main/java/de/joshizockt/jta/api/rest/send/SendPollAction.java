package de.joshizockt.jta.api.rest.send;

import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Poll;
import de.joshizockt.jta.api.requests.send.SendPollRequest;
import de.joshizockt.jta.api.rest.RestAction;

import java.util.List;

public class SendPollAction extends RestAction<Poll> {

    private final JTA jta;
    private final long chatId;

    private String question;
    private List<String> options;
    private boolean isAnonymous = false;
    private boolean isClosed = false;
    private int openPeriod = -1;
    private int closeDate = -1;
    private Poll.PollType type = Poll.PollType.REGULAR;
    private int correctOptionId = -1;
    private boolean allowsMultipleAnswers = false;

    public SendPollAction(JTA jta, long chatId) {
        super(null);
        this.jta = jta;
        this.chatId = chatId;
    }

    public SendPollAction setQuestion(String question) {
        this.question = question;
        return this;
    }

    public SendPollAction setOptions(List<String> options) {
        this.options = options;
        return this;
    }

    public SendPollAction setAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
        return this;
    }

    public SendPollAction setClosed(boolean isClosed) {
        this.isClosed = isClosed;
        return this;
    }

    public SendPollAction setOpenPeriod(int openPeriod) {
        this.openPeriod = openPeriod;
        return this;
    }

    public SendPollAction setCloseDate(int closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public SendPollAction setType(Poll.PollType type) {
        this.type = type;
        return this;
    }

    public SendPollAction setAllowsMultipleAnswers(boolean allowsMultipleAnswers) {
        this.allowsMultipleAnswers = allowsMultipleAnswers;
        return this;
    }

    public SendPollAction setCorrectOptionId(int correctOptionId) {
        this.correctOptionId = correctOptionId;
        return this;
    }

    @Override
    public Poll complete() {
        if(question == null)
            throw new NullPointerException("Question cannot be null");
        if(options == null)
            throw new NullPointerException("Options cannot be null");

        SendPollRequest request = new SendPollRequest(jta, chatId);

        request.question(question);
        request.options(options.toArray(String[]::new));
        request.anonymous(isAnonymous);
        request.allowsMultipleAnswers(allowsMultipleAnswers);
        request.isClosed(isClosed);

        if(openPeriod != -1 && closeDate != -1)
            throw new IllegalArgumentException("You cannot set both openPeriod and closeDate");

        if(openPeriod != -1)
            request.openPeriod(openPeriod);
        if(closeDate != -1)
            request.closeDate(closeDate);

        if(correctOptionId != -1 && type == Poll.PollType.QUIZ)
            request.correctOptionId(correctOptionId);

        request.type(type);

        return jta.getRequestHandler().execute(request);
    }

}
