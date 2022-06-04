package FileManager;

import tools.FileManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BuildTest {
    private final String path = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "FileManagerTest";
    private final String subDirectory = path + File.separator + "page";
    private final String buildPath = path + File.separator + "build";
    private final String buildSubDirectory = buildPath + File.separator + "page";
    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //Création du dossier de test
        FileManager.createDirectory(path);
        FileManager.createFile(path + File.separator + "index.md", "# Index");
        FileManager.createFile(path + File.separator + "config.json", "");

        FileManager.createDirectory(subDirectory);
        FileManager.createFile(subDirectory + File.separator + "page.md", "# Page");
        FileManager.createFile(subDirectory + File.separator + "image.jpg", "Image");

        //Build du dossier de test
        FileManager.build(path);
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        //Suppression du dossier test
        FileManager.deleteRecursive(path);
    }

    /* Test que le dossier build contienne les bons fichiers
    *  Et qu'ils aient les bonnes valeurs */
    @org.junit.jupiter.api.Test
    void testFolderBuildExists() {
        assertTrue(FileManager.fileExists(buildPath), "Vérifie que le répertoire build existe");
    }

    @org.junit.jupiter.api.Test
    void testIndexHtmlExists() {
        String p = buildPath + File.separator + "index.html";
        assertTrue(FileManager.fileExists(p),"Vérifie que le fichier index.html existe");
        assertEquals(FileManager.getContent(p), "<h1>Index</h1>\n", "Vérifie le contenu du fichier index.html");
    }

    @org.junit.jupiter.api.Test
    void testConfigurationNotCopied() {
        String p = buildPath + File.separator + "config.json";
        assertFalse(FileManager.fileExists(p), "Vérifie que le fichier config ne s'est pas copié dans le dossier build");
    }

    @org.junit.jupiter.api.Test
    void testFolderPageExists() {
        assertTrue(FileManager.fileExists(buildSubDirectory), "Vérifie que le sous-répertoire page existe");
    }

    @org.junit.jupiter.api.Test
    void testPageHtmlExists() {
        String p = buildSubDirectory + File.separator + "page.html";
        assertTrue(FileManager.fileExists(p), "Vérifie que page.html existe");
        assertEquals(FileManager.getContent(p), "<h1>Page</h1>\n", "Vérifie le contenu de page.html");
    }

    @org.junit.jupiter.api.Test
    void testImageJpgCopied() {
        String p = buildSubDirectory + File.separator + "image.jpg";
        assertTrue(FileManager.fileExists( p), "Vérifie que le fichier image.jpg existe");
        assertEquals(FileManager.getContent(p), "Image", "Vérifie le contenu du fichier image.jpg");
    }
}