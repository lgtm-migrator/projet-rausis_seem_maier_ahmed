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
    final static String remoteUrl = "https://github.com/justinrausis/testRepo.git";
    final static String token = "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";

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
