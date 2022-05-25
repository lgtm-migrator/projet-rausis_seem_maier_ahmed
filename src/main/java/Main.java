import java.util.concurrent.Callable;

import Subcommands.Build;
import Subcommands.New;

import java.net.URL;
import java.util.Properties;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;

@Command(name="Main",
         description = "C'est un site statique",
         subcommands = {
                        Build.class,
                        New.class
                       },
         versionProvider = Main.PropertiesVersionProvider.class)

public class Main implements Callable<Integer> {

    @Option(names = {"-V", "-version"}, versionHelp = true, description = "Print version info and exit")
    boolean versionRequested;

    @Override
    public Integer call() {
        System.out.println("Mesage au démarrage");
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
