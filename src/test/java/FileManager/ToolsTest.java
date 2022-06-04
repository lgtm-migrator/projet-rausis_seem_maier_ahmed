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
        FileManager.deleteRecursive(testDir);
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

    @org.junit.jupiter.api.Test
    void testIsDirectory() {
        String dir = testDir + File.separator + "test";
        String fakeDir = testDir + File.separator + "fake";
        String file = testDir + File.separator + "test.txt";

        FileManager.createDirectory(dir);
        FileManager.createFile(file, "content");

        assertTrue(FileManager.isDirectory(dir), "Vérifie que le dossier est bien un dossier");
        assertFalse(FileManager.isDirectory(fakeDir), "Vérifie qu'un dossier qui n'existe pas ne soit pas un dossier");
        assertFalse(FileManager.isDirectory(file), "Vérifie qu'un fichier ne soit pas un dossier");
    }

    @org.junit.jupiter.api.Test
    void testGetContent() {
        String file = testDir + File.separator + "test.txt";
        String fakeFile = testDir + File.separator + "fake.txt";
        String dir = testDir + File.separator + "test";
        String content = "Test content";
        FileManager.createFile(file, content);
        FileManager.createDirectory(dir);
        assertEquals(FileManager.getContent(file), content, "Vérifie que le contenu soit bon");
        assertNull(FileManager.getContent(fakeFile), "Vérifie que quand un fichier n'existe pas la valeur est null");
        assertNull(FileManager.getContent(dir), "Vérifie que le contenu d'un répertoire soit null");
    }

    @org.junit.jupiter.api.Test
    void testCopyFile() {
        String file = testDir + File.separator + "test.txt";
        String newFile = testDir + File.separator + "testCopy.txt";
        String fakeFile = testDir + File.separator + "fake.txt";
        String dir = testDir + File.separator + "test";
        String newDir = testDir + File.separator + "testCopy";
        String content = "Test content";

        FileManager.createFile(file, content);
        FileManager.createDirectory(dir);

        FileManager.copyFile(file, newFile);

        assertTrue(FileManager.fileExists(newFile), "Vérifie que le nouveau fichier a été créé");
        assertEquals(FileManager.getContent(newFile), content, "Vérifie que le contenu soit bon");
        assertFalse(FileManager.copyFile(dir, newDir), "Vérifie qu'il n'est pas possible de copier un dossier");
        assertFalse(FileManager.copyFile(fakeFile, newFile), "Vérifie que l'on ne peut pas copier un fichier qui n'existe pas");
    }

    @org.junit.jupiter.api.Test
    void testGetRelativePath() {
        String file = testDir + File.separator + "test" + File.separator + "test.txt";
        String relativePath = File.separator + "test" + File.separator + "test.txt";
        assertEquals(FileManager.getRelativePath(file, testDir), relativePath, "Check le chemin relatif");
    }

    @org.junit.jupiter.api.Test
    void testDeleteRecursive() {
        String dir = testDir + File.separator + "test";
        String fakeDir = testDir + File.separator + "fake";
        String file = dir + File.separator + "test.txt";
        String subDir = dir + File.separator + "sub";
        FileManager.createDirectory(dir);
        FileManager.createDirectory(subDir);
        FileManager.createFile(file, "");
        FileManager.deleteRecursive(dir);

        // Vérifie que de supprimer un dossier qui n'existe pas ne pose pas de problème
        FileManager.deleteRecursive(fakeDir);

        assertFalse(FileManager.fileExists(dir), "Vérifie que le dossier soit supprimé");

    }
}