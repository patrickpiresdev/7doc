package br.com.ptk;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class App {
    private static final String BASE_URL_STRING = "https://imdb-api.com/";
    private static final String USER_AGENT = "Java-http-client/" + System.getProperty("java.version");

    public static void main(String[] args) throws IOException {
        String pathToTop250Movies = "API/Top250Movies/" + getApiKey();
        String urlString = BASE_URL_STRING + pathToTop250Movies;

        HttpURLConnection connection = getConnection(urlString);
        if (unsuccessfulRequest(connection)) handleUnsuccessfulRequest();
        printResponseBody(connection);

        connection.disconnect();
    }

    private static HttpURLConnection getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("user-agent", USER_AGENT);
        return connection;
    }

    private static void printResponseBody(HttpURLConnection connection) throws IOException {
        Reader streamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(streamReader);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static void handleUnsuccessfulRequest() {
        // TODO: implement this method change it's name
        //  throw some exception -> search carefully which exception to throw
    }

    private static boolean unsuccessfulRequest(HttpURLConnection connection) throws IOException {
        int statusCode = connection.getResponseCode();
        return 200 <= statusCode && statusCode < 300;
    }

    private static String getApiKey() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\Users\\patri\\Documents\\projects\\7doc\\config.properties"));
        return properties.getProperty("api-key");
    }
}
