package com.panda4ev3r.search.api;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

// /search?pagesize=5&order=desc&sort=relevance&tagged=java&intitle=merge%20maps&site=stackoverflow
@Client("${stackoverflow.api.url}")
public interface StackOverflowHttpClient {

    @Get("/search?site=stackoverflow")
    ApiResponse<Question> search(
        @QueryValue("intitle") String query,
        @QueryValue("tag") String tag,
        @QueryValue("pagesize") int limit,
        @QueryValue("sort") String sort
    );
}
