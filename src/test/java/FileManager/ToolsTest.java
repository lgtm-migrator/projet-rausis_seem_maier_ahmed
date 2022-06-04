package FileManager;

import tools.FileManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ToolsTest {
    String testDir = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "FileManagerTest";

    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        FileManager.createDirectory(testDir);
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        FileManager.deleteRecursive(new File(testDir));
    }

    @org.junit.jupiter.api.Test
    void testCreateFile() {
        String path = testDir + File.separator + "test.txt";
        String content = "Test content";
        FileManager.createFile(path, content);
        assertTrue(FileManager.fileExists(path), "Vérifie que le fichier existe");
        assertEquals(FileManager.getContent(path), content, "Vérifie le contenu du fichier");
    }

    @org.junit.jupiter.api.Test
    void testCreateDirectory() {
        String path = testDir + File.separator + "test";
        FileManager.createDirectory(path);
        assertTrue(FileManager.fileExists(path), "Vérifie que le dossier existe");
    }

    @org.junit.jupiter.api.Test
    void testFileExists() {
        String dir = testDir + File.separator + "test";
        String fakeDir = testDir + File.separator + "fake";
        String file = testDir + File.separator + "test.txt";
        String fakeFile = testDir + File.separator + "fake.txt";
        FileManager.createDirectory(dir);
        FileManager.createFile(file, "");
        assertTrue(FileManager.fileExists(dir), "Vérifie qu'un dossier existant existe");
        assertTrue(FileManager.fileExists(file), "Vérifie qu'un fichier existant existe");
        assertFalse(FileManager.fileExists(fakeFile), "Vérifie qu'un fichier inexistant n'existe pas");
        assertFalse(FileManager.fileExists(fakeDir), "Vérifie qu'un dossier inexistant n'existe pas");
    }
}