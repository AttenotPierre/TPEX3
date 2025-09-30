import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        aliceInfo = new Etudiant(new Identite("1", "Durand", "Alice"), infoL3);
        bobInfo   = new Etudiant(new Identite("2", "Martin", "Bob"), infoL3);
        claraMaths= new Etudiant(new Identite("3", "Bernard", "Clara"), mathsL3);
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

    @Test
    void moyenneMatiere_ok() {
        Matiere maths = new Matiere("Maths");
        Matiere algo = new Matiere("Algo");
        infoL3.ajouterMatiere(maths, 2);
        infoL3.ajouterMatiere(algo, 3);

        Groupe g = new Groupe("Groupe E", infoL3);

        aliceInfo.ajouterNote(maths, 10);
        aliceInfo.ajouterNote(algo, 14);

        bobInfo.ajouterNote(maths, 16);
        bobInfo.ajouterNote(algo, 12);

        g.ajouterEtudiant(aliceInfo);
        g.ajouterEtudiant(bobInfo);

        // Moyenne Maths = (10 + 16) / 2 = 13
        assertEquals(13.0, g.calculerMoyenneMatiere(maths), 0.1);

        // Moyenne Algo = (14 + 12) / 2 = 13
        assertEquals(13.0, g.calculerMoyenneMatiere(algo), 0.1);
    }

    @Test
    void moyenneGenerale_ok() {
        Matiere maths = new Matiere("Maths");
        Matiere algo = new Matiere("Algo");
        infoL3.ajouterMatiere(maths, 2);
        infoL3.ajouterMatiere(algo, 3);

        Groupe g = new Groupe("Groupe F", infoL3);

        aliceInfo.ajouterNote(maths, 10); // moyenne Alice : (10*2 + 14*3)/5 = 12.4
        aliceInfo.ajouterNote(algo, 14);

        bobInfo.ajouterNote(maths, 16);   // moyenne Bob   : (16*2 + 12*3)/5 = 13.6
        bobInfo.ajouterNote(algo, 12);

        g.ajouterEtudiant(aliceInfo);
        g.ajouterEtudiant(bobInfo);

        // Moyenne générale du groupe = (12.4 + 13.6)/2 = 13.0
        assertEquals(13.0, g.calculerMoyenneGenerale(), 0.1);
    }

    @Test
    void triParMerite_ok() {
        Matiere maths = new Matiere("Maths");
        Matiere algo = new Matiere("Algo");
        infoL3.ajouterMatiere(maths, 2);
        infoL3.ajouterMatiere(algo, 3);

        Groupe g = new Groupe("Groupe G", infoL3);

        aliceInfo.ajouterNote(maths, 10); // Moyenne Alice : 12.4
        aliceInfo.ajouterNote(algo, 14);

        bobInfo.ajouterNote(maths, 16);   // Moyenne Bob : 13.6
        bobInfo.ajouterNote(algo, 12);

        g.ajouterEtudiant(aliceInfo);
        g.ajouterEtudiant(bobInfo);

        var tries = g.triParMerite();

        assertEquals(2, tries.size());
        assertEquals(bobInfo, tries.get(0));   // Bob en premier (moyenne 13.6)
        assertEquals(aliceInfo, tries.get(1)); // Alice en second (moyenne 12.4)
    }

}

