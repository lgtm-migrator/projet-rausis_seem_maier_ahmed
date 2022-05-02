import tools.ConfigInterpret;
import tools.FileManager;
import tools.PageCompiler;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageCompilerTest {

    FileManager f = new FileManager();
    String path = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "PageCompilerTest";
    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //Création du dossier de test
        Path p = Paths.get(path);
        try {
            Files.createDirectories(p);
            File f = new File(path + File.separator + PageCompiler.LAYOUT_NAME);

            if (f.createNewFile()) {
                String content = "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "   <meta charset=\"utf-8\">\n" +
                        "   <title>{{ site.titre }} | {{ page.titre }}</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "   {% include menu.html }\n" +
                        "   {{ content }}\n" +
                        "</body>\n" +
                        "</html>";
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            }

            f = new File(path + File.separator + "menu.html");

            if (f.createNewFile()) {
                String content =
                        "<ul>\n" +
                        "   <li><a href=\"/index.html\">home</a></li>\n" +
                        "   <li><a href=\"/content/page.html\">page</a></li>\n" +
                        "</ul>";
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            }
        } catch(Exception e){

        }

        //Initialisation des configurations du site
        ConfigInterpret.config("{titre: Mon site}");
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

    @org.junit.jupiter.api.Test
    void testContent() {
        String pageContent =
                "titre: Mon premier article\n" +
                "auteur: Bertil Chapuis\n" +
                "date: 2021-03-10\n" +
                "---\n" +
                "# Mon titre\n" +
                "\n" +
                "## Mon sous-titre\n" +
                "\n" +
                "Le contenu de mon article.\n" +
                "\n" +
                "![Une image](./image.png)";
        String correctResult =
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "   <meta charset=\"utf-8\">\n" +
                "   <title>Mon site | Mon premier article</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <ul>\n" +
                "      <li><a href=\"/index.html\">home</a></li>\n" +
                "      <li><a href=\"/content/page.html\">page</a></li>\n" +
                "   </ul>\n" +
                "   <h1>Mon titre</h1>\n" +
                "   <h2>Mon sous-titre</h2>\n" +
                "   <p>Le contenu de mon article.</p>\n" +
                "   <p><img src=\"./image.png\" alt=\"Une image\" /></p>\n" +
                "</body>\n" +
                "</html>";
        PageCompiler pc = new PageCompiler(path);
        assertEquals(pc.compilePage(pageContent), correctResult);
    }
}
