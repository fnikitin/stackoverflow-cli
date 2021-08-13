package com.panda4ev3r.search;

import com.panda4ev3r.api.Question;
import com.panda4ev3r.api.StackOverflowHttpClient;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import javax.inject.Inject;

// every picocli command has to implement either Runnable of Callable
@Command(name = "search", description = "Search questions matching criteria",
        mixinStandardHelpOptions = true)
public final class SearchCommand implements Runnable {

    @Option(names = {"-q", "--query"}, description = "Search phrase.")
    private String query = "";

    @Option(names = {"-t", "--tag"}, description = "Search inside specific tag.")
    private String tag = "";

    @Option(names = {"-n", "--limit"}, description = "Limit results. Default: 10")
    private int limit = 10;

    @Option(names = {"-s", "--sort-by"}, description = "Available values: relevance, votes, creation, action.")
    private String sort = "relevance";

    @Option(names = {"--verbose"}, description = "Print verbose output.")
    private boolean verbose;

    @Inject
    private StackOverflowHttpClient client;

    //@Inject
    //private SearchHttpRequest request;

    @Override
    public void run() {
        var response = client.search(query, tag, limit, sort);
        //var response = request.execute(query, tag, limit, sort);

        response.items.stream()
                .map(Question::formatQuestion)
                .forEach(System.out::println);

        if (verbose) {
            System.out.printf("Items size: %d | Quota max: %d | Has more: %s\n",
                    response.items.size(),
                    response.quotaMax,
                    response.quotaRemaining,
                    response.hasMore);
        }
    }
}
