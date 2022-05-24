import tools.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {
    FileManager f = new FileManager();
    String path = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "FileManagerTest";
    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //Création du dossier de test
        Path p = Paths.get(path);
        try {
            Files.createDirectories(p);
            File f = new File(path + File.separator + "index.md");

            if (f.createNewFile()) {
                String content = "";
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            }

            f = new File(path + File.separator + "config.json");

            if (f.createNewFile()) {
                String content = "";
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            }

            p = Paths.get(path + File.separator + "page");
            Files.createDirectories(p);
            f = new File(path + File.separator + "page" + File.separator + "page.md");

            if (f.createNewFile()) {
                String content = "";
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            }
            f = new File(path + File.separator + "page" + File.separator + "image.jpg");

            if (f.createNewFile()) {
                String content = "";
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            }
        } catch(Exception e){

        }

        FileManager f = new FileManager();
        f.build(path);
    }

    void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        //Suppression du dossier test
        File file = new File(path);
        deleteDirectory(file);
    }

    private boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }

    @org.junit.jupiter.api.Test
    void testFolderBuildExists() {
        assertEquals(fileExists(path + File.separator + "build"), true);
    }

    @org.junit.jupiter.api.Test
    void testIndexHtmlExists() {
        assertEquals(fileExists(path + File.separator + "build" + File.separator + "index.html"), true);
    }

    @org.junit.jupiter.api.Test
    void testConfigurationNotCopied() {
        assertEquals(!fileExists(path + File.separator + "build" + File.separator + "config.json"), true);
    }

    @org.junit.jupiter.api.Test
    void testFolderPageExists() {
        assertEquals(fileExists(path + File.separator + "build" + File.separator + "page"), true);
    }

    @org.junit.jupiter.api.Test
    void testPageHtmlExists() {
        assertEquals(fileExists(path + File.separator + "build" + File.separator + "page" + File.separator + "page.html"), true);
    }

    @org.junit.jupiter.api.Test
    void testImageJpgCopied() {
        assertEquals(fileExists(path + File.separator + "build" + File.separator + "page" + File.separator + "image.jpg"), true);
    }

}