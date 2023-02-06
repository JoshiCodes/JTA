package de.joshizockt.jta.api.requests.send;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.Message;
import de.joshizockt.jta.api.object.Poll;
import de.joshizockt.jta.api.requests.Request;

import java.util.Locale;

public class SendPollRequest extends Request<Poll> {

    private final JTA jta;

    public SendPollRequest(JTA jta, long chatId) {
        super("sendPoll", RequestMethod.POST);
        this.jta = jta;
        addData("chat_id", chatId);
    }

    public SendPollRequest question(String question) {
        addData("question", question);
        return this;
    }

    public SendPollRequest options(String... options) {
        JsonArray array = new JsonArray();
        for(String option : options) {
            array.add(option);
        }
        addData("options", array.toString());
        return this;
    }

    public SendPollRequest anonymous(boolean anonymous) {
        addData("is_anonymous", anonymous);
        return this;
    }

    public SendPollRequest type(Poll.PollType type) {
        addData("type", type.name().toLowerCase(Locale.ROOT));
        return this;
    }

    public SendPollRequest allowsMultipleAnswers(boolean allowsMultipleAnswers) {
        addData("allows_multiple_answers", allowsMultipleAnswers);
        return this;
    }

    public SendPollRequest correctOptionId(int correctOptionId) {
        addData("correct_option_id", correctOptionId);
        return this;
    }

    public SendPollRequest explanation(String explanation) {
        addData("explanation", explanation);
        return this;
    }

    public SendPollRequest openPeriod(int openPeriod) {
        addData("open_period", openPeriod);
        return this;
    }

    public SendPollRequest closeDate(int closeDate) {
        addData("close_date", closeDate);
        return this;
    }

    public SendPollRequest isClosed(boolean isClosed) {
        addData("is_closed", isClosed);
        return this;
    }

    public SendPollRequest disableNotification(boolean disableNotification) {
        addData("disable_notification", disableNotification);
        return this;
    }

    public SendPollRequest replyToMessageId(int replyToMessageId) {
        addData("reply_to_message_id", replyToMessageId);
        return this;
    }

    @Override
    public Poll parse(JsonObject jsonObject) {
        return Poll.fromJson(jta, jsonObject.get("result").getAsJsonObject());
    }

}
