package Subcommands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "new", description = "New")
public class New implements Callable<Integer> {
    @Override public Integer call() {
        System.out.println("New!");
        return 0;
    }
}