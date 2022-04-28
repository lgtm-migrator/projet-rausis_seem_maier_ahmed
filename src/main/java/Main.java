import java.util.concurrent.Callable;

import Subcommands.Build;
import Subcommands.New;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name="Main", subcommands = {
    Build.class,
    New.class
})

public class Main implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("Mesage au démarrage");
        return 0;
    }

    public static void main(String[] args) {
        int rc = new CommandLine(new Main()).execute(args);
        System.exit(rc);
    }

}
