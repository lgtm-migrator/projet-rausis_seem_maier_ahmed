package Subcommands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "-version", description = "Build")
public class Version implements Callable<Integer> {
    @Override public Integer call() {
        System.out.println("Build!");
        return 0;
    }
}
