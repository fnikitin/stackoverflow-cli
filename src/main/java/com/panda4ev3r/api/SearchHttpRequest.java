package com.panda4ev3r.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.uri.UriBuilder;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.zip.GZIPInputStream;

// Declarative HTTP client VS Java 11 Http client
// ----------------------------------------------
// A way to make it quicker (when app finishes, it takes 2 seconds to exit the app)
// by default the declarative http client uses  EvenLoopGroup (extends EventExecutorGroup) that is a good choice in most of the case
// however because this app it really short
// shutdownGracefully wait for 2 seconds (see AbstractEventExecutorGroup) for the app to release resources I guess
// the default mechanism to destroy EvenLoopGroup bean uses shutdownGracefully method that has this quiet period of 2 seconds
// in micronaut 2.0 there is no elegant solution to override this default behavior, however in 2.1 there should be a solution to this problem.
// https://github.com/micronaut-projects/micronaut-core/issues/3582
// as a workaround we can replace the client by the one from the regular java 11 http client
@Singleton
public final class SearchHttpRequest {

    private final ObjectMapper mapper;
    private final String stackOverflowApiUrl;

    public SearchHttpRequest(ObjectMapper mapper, @Value("${stackoverflow.api.url}") String stackOverflowApiUrl) {
        this.mapper = mapper;
        this.stackOverflowApiUrl = stackOverflowApiUrl;
    }

    public ApiResponse<Question> execute(String query, String tag, int limit, String sort) {
        var client = HttpClient.newHttpClient();

        var uri = UriBuilder.of(stackOverflowApiUrl)
                .path("/search")
                .queryParam("site", "stackoverflow")
                .queryParam("intitle", query)
                .queryParam("tagged", tag)
                .queryParam("pagesize", limit)
                .queryParam("sort", sort)
                .build();

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            // stackresponse api returned body compressed (lets assume it is not always the case)

            var input = response.headers()
                    .firstValue("Content-Encoding").orElse("")
                    .equals("gzip") ?
                    new GZIPInputStream(response.body()) :
                    response.body();
            return mapper.readValue(input, new TypeReference<>() {});

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
