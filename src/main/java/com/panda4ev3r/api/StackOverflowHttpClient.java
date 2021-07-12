package com.panda4ev3r.api;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

// This client is no longer used .. for the moment (see reasons in SearchHttpRequest)
// /search?pagesize=5&order=desc&sort=relevance&tagged=java&intitle=merge%20maps&site=stackoverflow
@Client("${stackoverflow.api.url}")
public interface StackOverflowHttpClient {

    @Get("/search?site=stackoverflow")
    ApiResponse<Question> search(
        @QueryValue("intitle") String query,
        @QueryValue("tagged") String tag,
        @QueryValue("pagesize") int limit,
        @QueryValue("sort") String sort
    );
}
