package de.joshizockt.jta.api.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;

public class JsonUtil {

    public static boolean getBoolean(JsonObject object, String key) {
        return getOrDefaultBoolean(object, key, false);
    }

    public static boolean getOrDefaultBoolean(JsonObject object, String key, boolean defaultValue) {
        JsonElement element = object.get(key);
        if(element == null) {
            return defaultValue;
        }
        return element.getAsBoolean();
    }

    public static String getOrDefaultString(JsonObject o, String key, String def) {
        if(o.has(key)) {
            return o.get(key).getAsString();
        }
        return def;
    }

    public static int getOrDefaultInt(JsonObject o, String key, int def) {
        if(o.has(key)) {
            return o.get(key).getAsInt();
        }
        return def;
    }

    public static JsonObject addObject(JsonObject json, String key, Object object) {
        if(object instanceof JsonElement) {
            json.add(key, (JsonElement) object);
        } else if(object instanceof String) {
            json.addProperty(key, (String) object);
        } else if(object instanceof Integer) {
            json.addProperty(key, (Integer) object);
        } else if(object instanceof Boolean) {
            json.addProperty(key, (Boolean) object);
        } else if(object instanceof Long) {
            json.addProperty(key, (Long) object);
        } else if(object instanceof Float) {
            json.addProperty(key, (Float) object);
        } else if(object instanceof Double) {
            json.addProperty(key, (Double) object);
        } else if(object instanceof Number) {
            json.addProperty(key, (Number) object);
        } else if(object instanceof Character) {
            json.addProperty(key, (Character) object);
        } else {
            json.addProperty(key, object.toString());
        }
        return json;
    }

    public static JsonObject parse(InputStream inputStream) {
        return new Gson().fromJson(new BufferedReader(new java.io.InputStreamReader(inputStream)), JsonObject.class);
    }

    public static JsonObject getOrDefaultObject(JsonObject result, String key, JsonObject def) {
        if(result.has(key)) {
            return result.get(key).getAsJsonObject();
        }
        return def;
    }

    public static String[] getAsStringArray(JsonArray options) {
        String[] strings = new String[options.size()];
        for(int i = 0; i < options.size(); i++) {
            strings[i] = options.get(i).getAsString();
        }
        return strings;
    }

    public static int[] getOrDefaultIntArray(JsonObject json, String optionIds, Object o) {
        if(json.has(optionIds)) {
            JsonArray array = json.get(optionIds).getAsJsonArray();
            int[] ints = new int[array.size()];
            for(int i = 0; i < array.size(); i++) {
                ints[i] = array.get(i).getAsInt();
            }
            return ints;
        }
        return new int[0];
    }
}
