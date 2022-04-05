package ch.Subcommands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "build", description = "Build")
public class Build implements Callable<Integer> {
    @Override public Integer call() {
        System.out.println("Build!");
        return 0;
    }
}
