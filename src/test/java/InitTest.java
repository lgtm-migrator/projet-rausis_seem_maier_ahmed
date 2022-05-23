import tools.FileManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitTest {

    FileManager f = new FileManager();
    String path = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "InitTest";

    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        f.createDirectory(path);
        f.init(path);
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        f.deleteRecursive(new File(path));
    }


    @org.junit.jupiter.api.Test
    void checkAllFilesExists() {
        f.fileExists(path + File.separator + "index.md");
        f.fileExists(path + File.separator + "config.json");
    }

    @org.junit.jupiter.api.Test
    void checkIndexContent() {
        String correctContent =
                "titre: Mon premier article\n" +
                "auteur: Bertil Chapuis\n" +
                "date: 2021-03-10\n" +
                "---\n" +
                "# Mon titre\n" +
                "## Mon sous-titre\n" +
                "Le contenu de mon article.\n" +
                "![Une image](./image.png)";
        try {
            String content = f.getContent(path + File.separator + "index.md");
            assertEquals(content, correctContent);
        } catch(Exception e){
            assert(false);
        }
    }

    @org.junit.jupiter.api.Test
    void checkConfigContent() {
        String correctContent =
                "{" +
                "   domaine: www.mon-site.com\n" +
                "   titre: \"Mon site\"" +
                "}";
        try {
            String content = f.getContent(path + File.separator + "config.json");
            assertEquals(content, correctContent);
        } catch(Exception e){
            assert(false);
        }
    }


}
