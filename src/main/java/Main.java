import java.util.concurrent.Callable;

import Subcommands.*;

import java.net.URL;
import java.util.Properties;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;

@Command(name="Main",
         description = "C'est un site statique, voici les differentes commandes disponible",
         subcommands = {
                        Build.class,
                         Init.class,
                         Clean.class,
                         Publish.class,
                       },
         versionProvider = Main.PropertiesVersionProvider.class)

public class Main implements Callable<Integer> {

    @Option(names = {"-V", "-version", "-v"}, versionHelp = true, description = "Print version info and exit")
    boolean versionRequested;

    @Override
    public Integer call() {
        System.out.println("Bienvenue sur le générateur de site statique du cours de dil.\n" +
                           "Pour connaître les commandes disponibles, veuillez soit vous référer au README,\n" +
                           "soit entrer un argument random afin de voir la liste des commandes disponibles.");
        return 0;
    }

    public static void main(String[] args) {
        int rc = new CommandLine(new Main()).execute(args);
        System.exit(rc);
    }

    static class PropertiesVersionProvider implements IVersionProvider {

        /**
         * Méthode permettant de récupérer la version du projet dans le fichier version.txt,
         * lequel contient une variable qui stock la version depuis le pom.xml à la compilation
         * @return un string contenant la version du projet.
         * @throws Exception an exception detailing what went wrong when obtaining version information
         */
        public String[] getVersion() throws Exception {
            URL url = getClass().getResource("/version.txt");
            if (url == null) {
                return new String[]{"No version.txt file found in the classpath. Is examples.jar in the classpath?"};
            }
            Properties properties = new Properties();
            properties.load(url.openStream());
            return new String[]{
                    properties.getProperty("application") + " " + properties.getProperty("version")
            };
        }
    }
}
