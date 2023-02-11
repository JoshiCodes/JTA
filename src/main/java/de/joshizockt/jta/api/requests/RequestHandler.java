package de.joshizockt.jta.api.requests;

import com.google.gson.JsonObject;
import de.joshizockt.jta.api.object.InputFile;
import de.joshizockt.jta.api.util.JsonUtil;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RequestHandler {

    public static final String API_URL = "https://api.telegram.org/bot%s/%s";
    public static final String CONTENT_TYPE = "application/json";

    private final String FORMATTED_URL;

    public RequestHandler(String token) {
        FORMATTED_URL = String.format(API_URL, token, "%s");
    }

    public <T> T executeWithFile(Request<T> request, String fieldName, InputFile file) {
        if(file == null) return execute(request);
        final String boundary = "*******";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(String.format(FORMATTED_URL, request.getApiMethod()));
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(request.getMethod().name());
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream outputStream = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

            if(request.hasData()) {
                HashMap<String, Object> data = request.getData();
                for (String key : data.keySet()) {
                    Object value = data.get(key);
                    writer.append("--" + boundary).append("\r\n");
                    writer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append("\r\n");
                    writer.append("Content-Type: text/plain; charset=").append(StandardCharsets.UTF_8.name()).append("\r\n");
                    writer.append("\r\n").append(value.toString()).append("\r\n").flush();
                }
            }

            writer.append("--" + boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"; filename=\"").append(file.name()).append("\"").append("\r\n");
            writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(file.name())).append("\r\n");
            writer.append("Content-Transfer-Encoding: binary").append("\r\n");
            writer.append("\r\n").flush();

            FileInputStream inputStream = new FileInputStream(file.file());
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append("\r\n").flush();

            writer.append("--" + boundary + "--").append("\r\n").flush();
            writer.close();

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] outputBuffer = new byte[1024];
            int length;
            while ((length = connection.getInputStream().read(outputBuffer)) != -1) {
                result.write(outputBuffer, 0, length);
            }
            String response = result.toString(StandardCharsets.UTF_8);
            connection.disconnect();

            JsonObject jsonObject = JsonUtil.parse(response);

            if(jsonObject == null || !jsonObject.get("ok").getAsBoolean()) {
                throw new NullPointerException("Response is null or api returned an error");
            }
            if(!jsonObject.has("result")) {
                throw new NullPointerException("The response does not contain a result object");
            }

            return request.parse(jsonObject);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

            if(jsonObject == null || !jsonObject.get("ok").getAsBoolean()) {
                throw new NullPointerException("Response is null or api returned an error");
            }
            if(!jsonObject.has("result")) {
                throw new NullPointerException("The response does not contain a result object");
            }

            return request.parse(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
