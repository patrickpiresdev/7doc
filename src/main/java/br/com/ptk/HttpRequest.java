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

        BufferedReader bufferedReader = getBufferedReader(connection, statusCode);
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);

        connection.disconnect();

        return buildResponse(statusCode, stringBuilder);
    }

    private HttpResponse buildResponse(int statusCode, StringBuilder stringBuilder) {
        HttpResponse httpResponse = new HttpResponse(statusCode);

        if (statusCode <= 299)
            httpResponse.setBody(stringBuilder.toString());
        else
            httpResponse.setError(stringBuilder.toString());

        return httpResponse;
    }

    private BufferedReader getBufferedReader(HttpURLConnection connection, int statusCode) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader((statusCode <= 299) ?
                connection.getInputStream() :
                connection.getErrorStream());

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader;
    }

    private HttpURLConnection getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("user-agent", USER_AGENT);
        return connection;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "urlString='" + urlString + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
