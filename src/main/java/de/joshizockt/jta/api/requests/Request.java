package de.joshizockt.jta.api.requests;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Objects;

public abstract class Request<T> {

    private final String apiMethod;
    private final RequestMethod method;

    private HashMap<String, Object> data;

    public Request(final String apiMethod, final RequestMethod method) {
        this.apiMethod = apiMethod;
        this.method = method;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public boolean hasData() {
        return data != null;
    }

    abstract public T parse(JsonObject jsonObject);

    protected void addData(String key, Object value) {
        if(data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

    public static enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

}
