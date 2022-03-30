package Subcommands;

import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

import tools.FileManager;

@Command(name = "build", description = "Build")
public class Build implements Callable<Integer> {

    @Parameters(index = "0", description = "The path to the folder.")
    private String path;

    @Override public Integer call() {
        String longPath = System.getProperty("user.dir") + "\\" + path;
        System.out.println(longPath);
        FileManager f = new FileManager();
        f.build(longPath);
        return 0;
    }
}
