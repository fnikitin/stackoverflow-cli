package com.panda4ev3r.search;

import com.panda4ev3r.search.api.ApiResponse;
import com.panda4ev3r.search.api.Question;
import com.panda4ev3r.search.api.StackOverflowHttpClient;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "search", description = "Search questions matching criteria",
        mixinStandardHelpOptions = true)
public final class SearchCommand implements Runnable {

    @Option(names = {"-q", "--query"}, description = "Search phrase.")
    String query = "";

    @Option(names = {"-t", "--tag"}, description = "Search inside specific tag.")
    String tag = "";

    @Option(names = {"-n", "--limit"}, description = "Limit results. Default: 10")
    int limit = 10;

    @Option(names = {"-s", "--sort-by"}, description = "Available values: relevance, votes, creation, action.")
    String sort = "relevance";

    @Option(names = {"--verbose"}, description = "Print verbose output.")
    boolean verbose;

    @Inject
    StackOverflowHttpClient client;

    @Override
    public void run() {
        ApiResponse<Question> response = client.search(query, tag, limit, sort);
        response.items
                .forEach(System.out::println);
    }
}
