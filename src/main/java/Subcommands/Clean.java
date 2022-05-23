package Subcommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tools.FileManager;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "clean", description = "Clean")
public class Clean implements Callable<Integer> {

    @Parameters(index = "0", description = "The path to the folder.")
    private String path;

    @Override public Integer call() {
        String longPath = System.getProperty("user.dir") + File.separator + path + File.separator + "build";;
        FileManager f = new FileManager();
        f.deleteRecursive(new File(longPath));
        return 0;
    }
}
