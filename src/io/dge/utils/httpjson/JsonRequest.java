package io.dge.utils.httpjson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.dge.utils.httpjson.Utils.Tuple;

/**
 * A class that contains a collection of factory methods allowing you to make GET,
 * POST, PUT, and DELETE requests and get back {@link JsonResponse} objects in return.
 *
 * The POST, PUT, and DELETE methods allow you to specify the request body. The body
 * must conform to the MIME type of {@code application/x-www-form-urlencoded}.
 */
public class JsonRequest {
    private static final String MIME_JSON = "application/json";
    private static final String MIME_FORM = "application/x-www-form-urlencoded";

    /**
     * Performs an HTTP GET request to the specified URL and expects a JSON response.
     *
     * @param url   The specified URL.
     * @return  A {@link JsonRequest} object.
     * @throws IOException  If a connection cannot be made.
     */
    public static JsonResponse get (String url) throws IOException {
        HttpURLConnection connection =
                (HttpURLConnection) new URL(url).openConnection();

        try {
            connection.setRequestProperty("Accept", MIME_JSON);

            InputStream in = new BufferedInputStream(connection.getInputStream());
            String responseBody = Utils.inputStreamToString(in);
            in.close();

            return new JsonResponse(connection.getResponseCode(), responseBody);
        } finally {
            connection.disconnect();
        }
    }

    private static Tuple<Integer, String> requestHelper (String method, String url,
                                                        String contents) throws
            IOException {
        contents = (contents == null) ? "" : contents;

        HttpURLConnection connection =
                (HttpURLConnection) new URL(url).openConnection();

        try {
            connection.setDoOutput(true); // sets this to make a POST request
            connection.setRequestMethod(method);
            connection.setRequestProperty("Accept", MIME_JSON);
            connection.setRequestProperty("Content-Type", MIME_FORM);
            connection.setRequestProperty("Content-Length",
                    String.valueOf(contents.length()));

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(contents.getBytes());
            out.close();

            InputStream in = new BufferedInputStream(connection.getInputStream());
            String responseBody = Utils.inputStreamToString(in);
            in.close();

            return new Tuple<Integer, String>(connection.getResponseCode(), responseBody);
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Performs an HTTP POST request to the specified URL and expects a JSON response.
     *
     * @param url   The specified URL
     * @param contents  The request body, which must conform to the Content-Type
     *                  "application/x-www-form-urlencoded". This argument is optional
     *                  and can be set to null.
     * @return  A {@link JsonRequest} object.
     * @throws IOException  If a connection cannot be made.
     */
    public static JsonResponse post (String url, String contents) throws IOException {
        Tuple<Integer, String> response = requestHelper("POST", url, contents);
        return new JsonResponse(response.getFirst(), response.getSecond());
    }

    /**
     * Performs an HTTP PUT request to the specified URL and expects a JSON response.
     *
     * @param url   The specified URL
     * @param contents  The request body, which must conform to the Content-Type
     *                  "application/x-www-form-urlencoded". This argument is optional
     *                  and can be set to null.
     * @return  A {@link JsonRequest} object.
     * @throws IOException  If a connection cannot be made.
     */
    public static JsonResponse put (String url, String contents) throws IOException {
        Tuple<Integer, String> response = requestHelper("PUT", url, contents);
        return new JsonResponse(response.getFirst(), response.getSecond());
    }

    /**
     * Performs an HTTP DELETE request to the specified URL and expects a JSON response.
     *
     * @param url   The specified URL
     * @param contents  The request body, which must conform to the Content-Type
     *                  "application/x-www-form-urlencoded". This argument is optional
     *                  and can be set to null.
     * @return  A {@link JsonRequest} object.
     * @throws IOException  If a connection cannot be made.
     */
    public static JsonResponse delete (String url, String contents) throws IOException {
        Tuple<Integer, String> response = requestHelper("DELETE", url, contents);
        return new JsonResponse(response.getFirst(), response.getSecond());
    }
}
