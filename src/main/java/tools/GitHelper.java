package tools;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class GitHelper {
    private String directory;

    /**
     * Constructeur de la classe le répertoire à push sur le répo git distant
     * (Dans notre cas le dossier build)
     * @param directory le répertoire à push
     */
    public GitHelper(String directory){
    }

    /**
     * Permet de publier le répertoire sur le répo distant
     * Si le répo n'a pas déjà été enregistré le demande à l'utilisateur
     * Même chose pour les crédentials (le token)
     * Ensuite publie sur le répo enregistré avec les crédentials enregistrés
     * Si la publication ne fonctionne pas efface le répo et les crédentials enregistrés
     */
    public void publish(){

    }

    /**
     * Check si un répo distant est enregistré
     * @return true si un répo est enregistré
     */
    private boolean hasRemoteUrl(){
        return false;
    }

    /**
     * Check si des crédentials sont enregistrés
     * @return tru si des crédentials sont enregistrés
     */
    private boolean hasCredentials(){
        return false;
    }

    /**
     * Demande à l'utilisateur le répo distant et le sotck dans le répertoire .git
     */
    private void askAndSaveRemoteUrl(){

    }

    /**
     * Demande à l'utilisateur les crédentials et les sotck dans le répertoire .git
     * (Demande uniquement le token)
     */
    private void askAndSaveCredentails(){

    }

    static public void test() throws GitAPIException, IOException {
        File dir = new File("../test");
        Git git = Git.init().setDirectory(dir).call();

        //Set remote origin
        StoredConfig config = git.getRepository().getConfig();
        /*config.setString("remote", "origin", "url", remoteUrl);
        config.setString("credentials", "token", "token", token);
        config.save();*/

        String tokenTest = config.getString("credentials", "token", "token");
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(tokenTest, "");

        AddCommand add = git.add();
        add.addFilepattern(".").call();
        CommitCommand commit = git.commit();
        commit.setMessage("test commit").call();

        PushCommand push = git.push();
        push.setCredentialsProvider(credentialsProvider);
        push.call();
    }
}
