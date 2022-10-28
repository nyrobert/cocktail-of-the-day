package com.thecocktaildb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

class ApiClient {
    private static final String URL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";
    private final HttpClient client;

    ApiClient(HttpClient client) {
        this.client = client;
    }

    public static ApiClient create() {
        return new ApiClient(
            HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10))
                .build()
        );
    }

    public HttpResponse<String> getCocktail() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}