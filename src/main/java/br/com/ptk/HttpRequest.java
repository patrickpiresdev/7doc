package br.com.ptk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static final String GET = "GET";
    private static final String USER_AGENT = "Java-http-client/" + System.getProperty("java.version");
    private final String urlString;
    private final String method;

    public HttpRequest(String urlString, String method) {
        this.urlString = urlString;
        this.method = method;
    }

    public HttpResponse execute() throws IOException {
        HttpURLConnection connection = getConnection(urlString);
        int statusCode = connection.getResponseCode();

        InputStreamReader inputStreamReader = new InputStreamReader((statusCode <= 299) ?
                connection.getInputStream() :
                connection.getErrorStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder bodyBuilder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null)
            bodyBuilder.append(line);

        connection.disconnect();

        return new HttpResponse(statusCode, bodyBuilder.toString());
    }

    private HttpURLConnection getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("user-agent", USER_AGENT);
        return connection;
    }
}
