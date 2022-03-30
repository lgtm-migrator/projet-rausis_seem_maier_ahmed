package Subcommands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

import tools.FileManager;

@Command(name = "build", description = "Build")
public class Build implements Callable<Integer> {
    @Override public Integer call() {
        FileManager f = new FileManager();
        f.build("C:\\Ecole\\DIL\\test");
        return 0;
    }
}
