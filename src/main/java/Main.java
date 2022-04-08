import java.awt.*;
import java.util.concurrent.Callable;

import Subcommands.Build;
import Subcommands.New;
import Subcommands.Version;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name="Main", subcommands = {
    Build.class,
    New.class,
    Version.class
})

public class Main implements Callable<Integer> {

    //Ajout de la sous-commande new

    //Ajout de la sous-commande clean

    //Ajout de la sous-commande build

    //Ajout de la sous-commande serve

    @Override
    public Integer call() {
        System.out.println("Mesage au démarrage");
        return 0;
    }

    public static void main(String[] args) {
        String s = "";
        if(args[0].charAt(0) == '-'){
            s = args[0].substring(1);
        }
        System.out.println(s);
        int rc = new CommandLine(new Main()).execute(s);
        System.exit(rc);
    }

}
