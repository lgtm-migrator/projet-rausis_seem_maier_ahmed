import tools.MarkdownToHtml;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkdownToHtmlTest {
    MarkdownToHtml converter = new MarkdownToHtml();
    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testMardownHeader() {
        String markdown = "# Titre 1";
        String htmlCorrect = "<h1>Titre 1</h1>\n";
        assertEquals(converter.convertToHtml(markdown), htmlCorrect, "La conversion en titre 1 n'est pas correct!");
        markdown = "## Titre 2";
        htmlCorrect = "<h2>Titre 2</h2>\n";
        assertEquals(converter.convertToHtml(markdown), htmlCorrect, "La conversion en titre 2 n'est pas correct!");
    }

    @org.junit.jupiter.api.Test
    void testMarkdownBold() {
        String markdown = "Un texte en **gras**";
        String htmlCorrect = "<p>Un texte en <strong>gras</strong></p>\n";
        assertEquals(converter.convertToHtml(markdown), htmlCorrect, "La conversion en gras n'est pas correct!");
    }

    @org.junit.jupiter.api.Test
    void testMarkdownMultipleLine() {
        String markdown = "# Titre \n" +
                            "Un texte en **gras**";
        String htmlCorrect = "<h1>Titre</h1>\n" +
                            "<p>Un texte en <strong>gras</strong></p>\n";
        assertEquals(converter.convertToHtml(markdown), htmlCorrect, "La conversion avec plusieurs lignes n'est pas correct!");
    }

}