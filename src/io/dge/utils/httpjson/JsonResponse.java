package io.dge.utils.httpjson;

import com.google.gson.Gson;

/**
 * A JsonResponse is a container for an HTTP response in JSON. Use
 * {@link #getResponseCode()} to get the HTTP response code,
 * and {@link #fromJson(Class)} to deserialize the JSON response string into a class
 * via Google GSON.
 *
 * Use the {@link JsonRequest} factory to create objects of this class.
 */
public class JsonResponse {
    private int responseCode;
    private String body;

    JsonResponse(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;
    }

    /**
     * Returns the HTTP response code.
     *
     * @return  The HTTP response code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Uses the Google GSON library to decode the HTTP response body into an instance
     * of a class whose structure mirrors that of the JSON structure. See
     * {@linkplain https://sites.google.com/site/gson/gson-user-guide#TOC-Using-Gson}
     * for more information.
     *
     * @param jsonObjectClass   The {@code Class} object whose structure reflects the
     *                          JSON response. The class is represented by the
     *                          parameter {@code &lt;T&gt;}.
     * @param <T>   The class name
     * @return  An object of the class whose structure reflects the JSON response.
     * @throws com.google.gson.JsonSyntaxException  If JSON parsing fails
     */
    public <T> T fromJson(Class<T> jsonObjectClass) {
        Gson gson = new Gson();
        return gson.fromJson(this.body, jsonObjectClass);
    }
}
