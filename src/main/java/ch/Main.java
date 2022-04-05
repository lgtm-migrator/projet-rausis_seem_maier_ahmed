package ch;

import java.util.concurrent.Callable;

import ch.Subcommands.Build;
import ch.Subcommands.New;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name="ch.Main", subcommands = {
    Build.class,
    New.class,

})

public class Main implements Callable<Integer> {


    @Override
    public Integer call() {
        System.out.println("Mesage au démarrage");
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ch.Main()).execute(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

}
