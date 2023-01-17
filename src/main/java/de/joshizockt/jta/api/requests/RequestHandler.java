package de.joshizockt.jta.api.requests;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.util.JsonUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RequestHandler {

    public static final String API_URL = "https://api.telegram.org/bot%s/%s";
    public static final String CONTENT_TYPE = "application/json";

    private final String FORMATTED_URL;

    public RequestHandler(String token) {
        FORMATTED_URL = String.format(API_URL, token, "%s");
    }

    public <T> T execute(Request<T> request) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(String.format(FORMATTED_URL, request.getApiMethod()));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.getMethod().name());
            connection.setRequestProperty("Content-Type", CONTENT_TYPE);
            connection.setRequestProperty("Accept", "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            if(request.hasData()) {
                HashMap<String, Object> data = request.getData();
                JsonObject dataObject = new JsonObject();
                for(String key : data.keySet()) {
                    dataObject = JsonUtil.addObject(dataObject, key, data.get(key));
                }
                String dataString = dataObject.toString();
                //connection.setRequestProperty("Content-Length", String.valueOf(dataString.length()));

                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = dataString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

            }
            JsonObject jsonObject = JsonUtil.parse(connection.getInputStream());
            return request.parse(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
