import static org.junit.jupiter.api.Assertions.*;

class exempleTest {

    // le code dans cette méthode est exécuté avant chaque test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    // le code dans cette méthode est exécuté après chaque test
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    // Ci-dessous des examples de tests. On peut donner le nom qu'on veut aux méthodes.

    // example de test qui passe
    @org.junit.jupiter.api.Test
    void monSuperTestQuiMarche() {
        int variable = 42;

        // les arguments de assertEquals sont :
        // - la valeur correcte
        // - la valeur à tester
        // - un texte qui va s'afficher quand le test ne passe pas
        assertEquals(42, variable, "la variable doit valoir 42");
    }

    // example de test qui passe pas
    // (si on décomente le code ci-dessous, github va refuser de merge dans la branche principale
    // vu que le test passe pas)

    
//    @org.junit.jupiter.api.Test
//    void monSuperTestQuiMarchePas() {
//        int variable = 0;
//
//        assertEquals(42, variable, "la variable doit valoir 42");
//
//    }

}