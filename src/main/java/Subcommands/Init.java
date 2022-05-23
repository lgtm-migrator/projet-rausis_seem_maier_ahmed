package Subcommands;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import tools.FileManager;

import java.util.concurrent.Callable;

@Command(name = "init", description = "init")
public class Init implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The path to the folder.")
    private String path;

    @Override public Integer call() throws IOException {
        String longPath = System.getProperty("user.dir") + File.separator + path;
        FileManager fm = new FileManager();
        fm.init(longPath);
        return 0;
    }


}
