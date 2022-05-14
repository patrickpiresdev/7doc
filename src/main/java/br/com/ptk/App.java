package br.com.ptk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class App {
    private static final String BASE_URL_STRING = "https://imdb-api.com/";
    private final static String APPLICATION_PROPERTIES = "C:\\Users\\patri\\Documents\\projects\\code\\7doc\\application.properties";

    public static void main(String[] args) throws IOException {
        String pathToTop250Movies = "API/Top250Movies/" + getApiKey();
        String urlString = BASE_URL_STRING + pathToTop250Movies;

        HttpRequest httpRequest = new HttpRequest(urlString, HttpRequest.GET);
        HttpResponse response = httpRequest.execute();
        System.out.println(response.getBody());
    }

    private static String getApiKey() throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(APPLICATION_PROPERTIES)));
        return properties.getProperty("api-key");
    }
}
