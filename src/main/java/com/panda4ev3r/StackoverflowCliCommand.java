package com.panda4ev3r;

import com.panda4ev3r.search.SearchCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

// https://www.youtube.com/watch?v=Xdcg4Drg1hc&ab_channel=SzymonStepniak
@Command(name = "stackoverflow-cli", description = "...",
        mixinStandardHelpOptions = true, subcommands = { SearchCommand.class })
public class StackoverflowCliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(StackoverflowCliCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
