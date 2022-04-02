package br.com.ptk;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class App {
    private static final String BASE_URL_STRING = "https://imdb-api.com/";

    public static void main(String[] args) throws IOException {
        String pathToTop250Movies = "API/Top250Movies/" + getApiKey();
        String urlString = BASE_URL_STRING + pathToTop250Movies;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("user-agent", "Java-http-client/" + System.getProperty("java.version"));

        Reader streamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(streamReader);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static String getApiKey() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\Users\\patri\\Documents\\projects\\7doc\\config.properties"));
        return properties.getProperty("api-key");
    }
}
