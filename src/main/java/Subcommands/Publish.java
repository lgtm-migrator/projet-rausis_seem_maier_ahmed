package Subcommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import tools.FileManager;
import tools.GitHelper;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "publish", description = "Publie le dossier build sur un répertoire git distant")
public class Publish implements Callable<Integer> {

    @Parameters(index = "0", description = "Le chemin au dossier racine du projet (le dossier qui contient un dossier build)")
    private String path;

    @Option(names = {"-c", "--clear"}, description = "Supprime les informations enregistrées (lien au répo distant et crédentials")
    boolean clear;

    @Override public Integer call() {
        File dir = new File(path + File.separator + "Build");
        if(!FileManager.fileExists(dir.getAbsolutePath())){
            System.out.println("Il n'y a pas de dossier build dans le dossier fournit");
            return 0;
        }
        GitHelper git;
        try{
             git = new GitHelper(dir);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        if(clear){
            git.delSaveRemoteUrlAndCredentials();
            return 0;
        }
        git.publish();
        return 0;
    }
}
