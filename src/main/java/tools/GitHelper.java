package tools;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GitHelper {
    private File directory;
    private Git git;
    private StoredConfig config;

    /**
     * Constructeur de la classe le répertoire à push sur le répo git distant
     * (Dans notre cas le dossier build)
     * @param directory le répertoire à push
     */
    public GitHelper(File directory) throws GitAPIException {
        this.directory = directory;
        git = Git.init().setDirectory(directory).call();
        config = git.getRepository().getConfig();
    }

    /**
     * Permet de publier le répertoire sur le répo distant
     * Si le répo n'a pas déjà été enregistré le demande à l'utilisateur
     * Même chose pour les crédentials (le token)
     * Ensuite publie sur le répo enregistré avec les crédentials enregistrés
     * Si la publication ne fonctionne pas efface le répo et les crédentials enregistrés
     */
    public void publish(){
        if(!hasRemoteUrl()) askAndSaveRemoteUrl();
        if(!hasCredentials()) askAndSaveCredentails();

        //Créer le commit et le push
        try {
            AddCommand add = git.add();
            add.addFilepattern(".").call();
            CommitCommand commit = git.commit();
            commit.setMessage("Automatique commit").call();

            PushCommand push = git.push();
            push.setCredentialsProvider(getCredentials());
            push.call();
        } catch (Exception e){
            //Si une erreur survient lors du push (poblème d'accès au répo)
            //Delete les informations sauvegardées pour en redemander des nouvelles
            //Lors du prochain publish de l'utilisateur
            System.out.println(e.getMessage());
            System.out.println("Une erreur est survenue lors de la publication");
            delSaveRemoteUrlAndCredentials();
        }
    }

    private CredentialsProvider getCredentials(){
        String token = config.getString("credentials", "token", "token");
        return new UsernamePasswordCredentialsProvider(token, "");
    }

    /**
     * Check si un répo distant est enregistré
     * @return true si un répo est enregistré
     */
    private boolean hasRemoteUrl(){
        String url = config.getString("remote", "origin", "url");
        return url != null;
    }

    /**
     * Check si des crédentials sont enregistrés
     * @return tru si des crédentials sont enregistrés
     */
    private boolean hasCredentials(){
        String token = config.getString("credentials", "token", "token");
        return token != null;
    }

    /**
     * Demande à l'utilisateur le répo distant et le sotck dans le répertoire .git
     */
    private void askAndSaveRemoteUrl(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Entrer l'url du répertoire git distant: ");
        String str= sc.nextLine();
        config.setString("remote", "origin", "url", str);
    }

    /**
     * Demande à l'utilisateur les crédentials et les sotck dans le répertoire .git
     * (Demande uniquement le token)
     */
    private void askAndSaveCredentails(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Entrer le token pour s'authentifier: ");
        String str= sc.nextLine();
        config.setString("credentials", "token", "token", str);
    }

    /**
     * Suprpime les crédentials et l'url du répo enregistrés
     */
    public void delSaveRemoteUrlAndCredentials(){
        delSavedCredentials();
        delSavedRemoteUrl();
    }

    /**
     * Supprime l'url du répo enregistré
     */
    private void delSavedRemoteUrl(){
        config.setString("remote", "origin", "url", null);
    }

    /**
     * Supprime les crédentials enregistrés
     */
    private void delSavedCredentials(){
        config.setString("credentials", "token", "token", null);
    }

    static public void test() throws GitAPIException, IOException {
        File dir = new File("../test");
        Git git = Git.init().setDirectory(dir).call();

        //Set remote origin
        StoredConfig config = git.getRepository().getConfig();
        /*config.setString("remote", "origin", "url", remoteUrl);
        config.setString("credentials", "token", "token", token);
        config.save();*/

        String test = config.getString("credentials", "token", "tokenasdf");

        /*String tokenTest = config.getString("credentials", "token", "token");
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(tokenTest, "");

        AddCommand add = git.add();
        add.addFilepattern(".").call();
        CommitCommand commit = git.commit();
        commit.setMessage("test commit").call();

        PushCommand push = git.push();
        push.setCredentialsProvider(credentialsProvider);
        push.call();*/
    }
}
