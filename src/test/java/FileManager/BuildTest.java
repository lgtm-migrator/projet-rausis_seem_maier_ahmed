package FileManager;

import tools.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BuildTest {
    String path = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "FileManager.FileManagerTest";
    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //Création du dossier de test
        FileManager.createDirectory(path);
        FileManager.createFile(path + File.separator + "index.md", "#Index");
        FileManager.createFile(path + File.separator + "config.json", "");

        String subDirectory = path + File.separator + "page";
        FileManager.createDirectory(subDirectory);
        FileManager.createFile(subDirectory + File.separator + "page.md", "#Page");
        FileManager.createFile(subDirectory + File.separator + "image.jpg", "image");

        //Build du dossier de test
        FileManager.build(path);
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        //Suppression du dossier test
        FileManager.deleteRecursive(new File(path));
    }

    @org.junit.jupiter.api.Test
    void testFolderBuildExists() {
        assertTrue(FileManager.fileExists(path + File.separator + "build"));
    }

    @org.junit.jupiter.api.Test
    void testIndexHtmlExists() {
        assertTrue(FileManager.fileExists(path + File.separator + "build" + File.separator + "index.html"));
    }

    @org.junit.jupiter.api.Test
    void testConfigurationNotCopied() {
        assertFalse(FileManager.fileExists(path + File.separator + "build" + File.separator + "config.json"));
    }

    @org.junit.jupiter.api.Test
    void testFolderPageExists() {
        assertTrue(FileManager.fileExists(path + File.separator + "build" + File.separator + "page"));
    }

    @org.junit.jupiter.api.Test
    void testPageHtmlExists() {
        assertTrue(FileManager.fileExists(path + File.separator + "build" + File.separator + "page" + File.separator + "page.html"));
    }

    @org.junit.jupiter.api.Test
    void testImageJpgCopied() {
        assertTrue(FileManager.fileExists(path + File.separator + "build" + File.separator + "page" + File.separator + "image.jpg"));
    }

}