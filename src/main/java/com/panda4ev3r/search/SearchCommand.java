package com.panda4ev3r.search;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

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

    @Override
    public void run() {
        System.out.println("Search command running...");
    }
}
