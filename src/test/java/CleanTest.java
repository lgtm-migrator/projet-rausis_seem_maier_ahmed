import tools.FileManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CleanTest {

    FileManager f = new FileManager();
    String path = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "CleanTest";
    String buildPath = path + File.separator + "build";

    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        f.createDirectory(path);
        f.createDirectory(buildPath);
        f.createFile(buildPath + File.separator + "test.html", "Test");
        f.deleteRecursive(new File(buildPath));
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        f.deleteRecursive(new File(path));
    }


    @org.junit.jupiter.api.Test
    void checkDirectoryBuildDoesNotExists() {
        assertEquals(f.fileExists(buildPath), false);
    }


}
