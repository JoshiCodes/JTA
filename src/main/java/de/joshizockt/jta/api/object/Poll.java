package de.joshizockt.jta.api.object;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.util.JsonUtil;

public abstract class Poll {

    public static Poll fromJson(JTA jta, JsonObject json) {
        json = json.get("poll").getAsJsonObject();
        String id = json.get("id").getAsString();
        String question = json.get("question").getAsString();
        PollOption[] options = new PollOption[json.get("options").getAsJsonArray().size()];
        for(int i = 0; i < options.length; i++) {
            options[i] = PollOption.fromJson(json.get("options").getAsJsonArray().get(i).getAsJsonObject());
        }
        int totalVoterCount = json.get("total_voter_count").getAsInt();
        boolean isClosed = json.get("is_closed").getAsBoolean();
        boolean isAnonymous = json.get("is_anonymous").getAsBoolean();
        PollType type = PollType.valueOf(json.get("type").getAsString().toUpperCase());
        boolean allowsMultipleAnswers = json.get("allows_multiple_answers").getAsBoolean();
        int correctOptionId = JsonUtil.getOrDefaultInt(json, "correct_option_id", -1);
        String explanation = JsonUtil.getOrDefaultString(json, "explanation", null);
        int openPeriod = JsonUtil.getOrDefaultInt(json, "open_period", -1);
        int closeDate = JsonUtil.getOrDefaultInt(json, "close_date", -1);
        return new Poll() {

            @Override
            public String getQuestion() {
                return question;
            }

            @Override
            public PollOption[] getOptions() {
                return options;
            }

            @Override
            public int getTotalVoterCount() {
                return totalVoterCount;
            }

            @Override
            public boolean isAnonymous() {
                return isAnonymous;
            }

            @Override
            public PollType getType() {
                return type;
            }

            @Override
            public boolean allowsMultipleAnswers() {
                return allowsMultipleAnswers;
            }

            @Override
            public int getCorrectOptionId() {
                return correctOptionId;
            }

            @Override
            public String getExplanation() {
                return explanation;
            }

            @Override
            public int getOpenPeriod() {
                return openPeriod;
            }

            @Override
            public int getCloseDate() {
                return closeDate;
            }

            @Override
            public boolean isClosed() {
                return isClosed;
            }

            @Override
            public JTA getJTA() {
                return jta;
            }

            @Override
            public String getId() {
                return id;
            }

        };
    }

    public abstract JTA getJTA();
    public abstract String getId();

    public abstract String getQuestion();
    public abstract PollOption[] getOptions();
    public abstract int getTotalVoterCount();
    public abstract boolean isAnonymous();
    public abstract PollType getType();
    public abstract boolean allowsMultipleAnswers();
    public abstract int getCorrectOptionId();
    public abstract String getExplanation();
    public abstract int getOpenPeriod();
    public abstract int getCloseDate();
    public abstract boolean isClosed();


    public enum PollType {
        QUIZ,
        REGULAR;
    }

    public interface PollOption {

        static PollOption fromJson(JsonObject options) {
            String text = options.get("text").getAsString();
            int voterCount = options.get("voter_count").getAsInt();
            return new PollOption() {

                @Override
                public String getText() {
                    return text;
                }

                @Override
                public int getVoterCount() {
                    return voterCount;
                }

            };
        }

        String getText();
            int getVoterCount();

    }

}
