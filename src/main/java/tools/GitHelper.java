package tools;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
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
        config.setString("remote", "origin", "url", remoteUrl);
        config.save();


        AddCommand add = git.add();
        add.addFilepattern(".").call();
        CommitCommand commit = git.commit();
        commit.setMessage("initial commit").call();
        git.push().call();
    }
}
