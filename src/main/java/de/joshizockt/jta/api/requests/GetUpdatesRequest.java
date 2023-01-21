package de.joshizockt.jta.api.requests;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.JTA;
import de.joshizockt.jta.api.object.IUpdate;
import de.joshizockt.jta.api.util.UpdateParser;

import java.util.ArrayList;
import java.util.List;

public class GetUpdatesRequest extends Request<List<IUpdate<?>>> {

    private final JTA jta;

    public GetUpdatesRequest(JTA jta) {
        super("getUpdates", RequestMethod.POST);
        this.jta = jta;

    }

    public GetUpdatesRequest offset(int offset) {
        addData("offset", offset);
        return this;
    }

    public GetUpdatesRequest limit(int limit) {
        addData("limit", limit);
        return this;
    }

    public GetUpdatesRequest timeout(int timeout) {
        addData("timeout", timeout);
        return this;
    }

    public GetUpdatesRequest allowedUpdates(String... allowedUpdates) {
        addData("allowed_updates", allowedUpdates);
        return this;
    }

    @Override
    public List<IUpdate<?>> parse(JsonObject jsonObject) {
        List<IUpdate<?>> list = new ArrayList<>();
        for (int i = 0; i < jsonObject.get("result").getAsJsonArray().size(); i++) {
            final JsonObject json = jsonObject.get("result").getAsJsonArray().get(i).getAsJsonObject();
            UpdateParser parser = new UpdateParser(json);
            IUpdate<?> update = parser.parse(jta);
            if(update != null) {
                list.add(update);
            }
        }
        return list;
    }

}
