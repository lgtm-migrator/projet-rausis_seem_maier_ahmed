package Subcommands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "version", description = "Version")
public class Version implements Callable<Integer>{
    @Override public Integer call() {
        var i = getClass().getPackage().getImplementationVersion();
        System.out.println("version!");
        return 0;
    }
}
