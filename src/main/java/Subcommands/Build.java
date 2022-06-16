package Subcommands;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.concurrent.Callable;

import tools.FileManager;

@Command(name = "build", description = "Permet la création d'un site statique à partir de template")
public class Build implements Callable<Integer> {

    @Parameters(index = "0", description = "The path to the folder.")
    private String path;

    @Option(names = {"-w", "--watch"}, description = "Re-build s'il y a des modifications")
    boolean watch;

    @Override public Integer call() {
        String longPath = FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
        System.out.println("Build " + longPath + " en cours...");
        FileManager f = new FileManager();
        if(f.build(longPath)){
            System.out.println("Build terminé!");
            if(watch){
                f.watch(longPath);
            }
        } else {
            System.out.println("Une erreur s'est produite lors du build!");
        }
        return 0;
    }
}
