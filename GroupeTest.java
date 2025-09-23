import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class GroupeTest {

    private Formation infoL3;
    private Formation mathsL3;
    private Etudiant aliceInfo;
    private Etudiant bobInfo;
    private Etudiant claraMaths;

    @BeforeEach
    void setUp() {

        infoL3 = new Formation("Informatique L3");
        mathsL3 = new Formation("Mathématiques L3");


        aliceInfo = new Etudiant(new Identite("1", "Durand","Alice"), infoL3);
        bobInfo   = new Etudiant(new Identite("2", "Martin","Bob"), infoL3);
        claraMaths= new Etudiant(new Identite("3", "didi","Bernard"), mathsL3);
    }

    @Test
    void ajoutMemeFormation_ok() {
        Groupe g = new Groupe("Groupe A", infoL3);

        assertEquals(0, g.taille());
        assertTrue(g.ajouterEtudiant(aliceInfo));
        assertEquals(1, g.taille());
        assertTrue(g.contient(aliceInfo));

        assertTrue(g.ajouterEtudiant(bobInfo));
        assertEquals(2, g.taille());
        assertTrue(g.contient(bobInfo));

        // pas de doublon
        assertFalse(g.ajouterEtudiant(aliceInfo));
        assertEquals(2, g.taille());
    }

    @Test
    void ajoutFormationDifferente_exception() {
        Groupe g = new Groupe("Groupe B", infoL3);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            g.ajouterEtudiant(claraMaths);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("formation"));
        assertEquals(0, g.taille());
    }

    @Test
    void ajoutNull_exception() {
        Groupe g = new Groupe("Groupe C", infoL3);
        assertThrows(IllegalArgumentException.class, () -> g.ajouterEtudiant(null));
    }

    @Test
    void suppression_ok_et_idempotente() {
        Groupe g = new Groupe("Groupe D", infoL3);
        g.ajouterEtudiant(aliceInfo);
        g.ajouterEtudiant(bobInfo);
        assertEquals(2, g.taille());

        assertTrue(g.supprimerEtudiant(aliceInfo));
        assertEquals(1, g.taille());
        assertFalse(g.contient(aliceInfo));

        assertFalse(g.supprimerEtudiant(aliceInfo)); // déjà supprimé
        assertEquals(1, g.taille());

        assertFalse(g.supprimerEtudiant(claraMaths)); // jamais ajouté
        assertEquals(1, g.taille());

        assertFalse(g.supprimerEtudiant(null)); // sécurité
        assertEquals(1, g.taille());
    }

    @Test
    void constructeur_validation() {
        assertThrows(IllegalArgumentException.class, () -> new Groupe("", infoL3));
        assertThrows(IllegalArgumentException.class, () -> new Groupe("Nom", null));
    }
}
