import tools.ConfigInterpret;
import static org.junit.jupiter.api.Assertions.*;

class ConfigInterpretTest {

    String jsonString = "{\"name\":\"John\", \"age\":30, \"car\":null}";

    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    // Test si on récupère bien les strings attendu par rapport aux informations
    // qui ont été stockées dans l'interpréteur de configuration
    @org.junit.jupiter.api.Test
    void stockageEtRecuperationDonnee() {

        ConfigInterpret config = ConfigInterpret.getInstance();
        ConfigInterpret.config(jsonString);

        String test1 = config.getConfig("name");
        String test2 = config.getConfig("age");
        String test3 = config.getConfig("car");
        String test4 = config.getConfig("suuu");

        assertEquals("John", test1, "Le nom récupéré n'est pas John");
        assertEquals("30", test2, "L'age récupéré n'est pas 30");
        assertEquals("null", test3, "Le car récupéré n'est pas null");
        assertEquals("suuu", test4, "Le message récupéré n'est pas suuu");
    }

    // Test si on récupère bien les valeur corrects depuis un objet qui n'a pas appelé la méthode config
    @org.junit.jupiter.api.Test
    void testAccesSecondObjet() {
        ConfigInterpret config = ConfigInterpret.getInstance();
        ConfigInterpret.config(jsonString);
        ConfigInterpret config2 = ConfigInterpret.getInstance();

        String test1 = config2.getConfig("name");
        String test2 = config2.getConfig("age");
        String test3 = config2.getConfig("car");
        String test4 = config2.getConfig("suuu");

        assertEquals("John", test1, "Le nom récupéré n'est pas John");
        assertEquals("30", test2, "L'age récupéré n'est pas 30");
        assertEquals("null", test3, "Le car récupéré n'est pas null");
        assertEquals("suuu", test4, "Le message récupéré n'est pas suuu");
    }
}