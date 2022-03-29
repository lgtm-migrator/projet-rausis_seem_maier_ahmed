package Subcommands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "init", description = "init")
public class Init implements Callable<Integer> {
    @Override public Integer call() {
        Path path =  Paths.get("/mon/site");
        if(Files.exists(path)){
          
        } else {

        }

        System.out.println("Init");
        return 0;
    }


}
