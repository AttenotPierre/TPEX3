import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupeTriTest {

    private Formation infoL3;
    private Groupe g;

    private Etudiant eAlice;   // nom: Durand, prenom: Alice
    private Etudiant eBob;     // nom: Bernard, prenom: Bob
    private Etudiant eChloe;   // nom: Bernard, prenom: Chloe
    private Etudiant eZoe;     // nom: Zozo,   prenom: Zoe

    @BeforeEach
    void setUp() {
        infoL3 = new Formation("Informatique L3");

        // les matières / coefficients ne sont pas nécessaires ici
        g = new Groupe("G1", infoL3);

        eAlice = new Etudiant(new Identite("N001", "Durand", "Alice"), infoL3);
        eBob   = new Etudiant(new Identite("N002", "Bernard", "Bob"), infoL3);
        eChloe = new Etudiant(new Identite("N003", "Bernard", "Chloe"), infoL3);
        eZoe   = new Etudiant(new Identite("N004", "Zozo", "Zoe"), infoL3);

        g.ajouterEtudiant(eAlice);
        g.ajouterEtudiant(eBob);
        g.ajouterEtudiant(eChloe);
        g.ajouterEtudiant(eZoe);
    }

    @Test
    void testTriAlpha() {
        List<Etudiant> tri = g.triAlpha();
        // Ordre attendu (nom ASC, prenom ASC, puis NIP)
        // Bernard Bob, Bernard Chloe, Durand Alice, Zozo Zoe
        assertEquals(eBob,   tri.get(0));
        assertEquals(eChloe, tri.get(1));
        assertEquals(eAlice, tri.get(2));
        assertEquals(eZoe,   tri.get(3));
    }

    @Test
    void testTriAntiAlpha() {
        List<Etudiant> tri = g.triAntiAlpha();
        // ordre inverse du précédent
        assertEquals(eZoe,   tri.get(0));
        assertEquals(eAlice, tri.get(1));
        assertEquals(eChloe, tri.get(2));
        assertEquals(eBob,   tri.get(3));
    }

    @Test
    void testTriStableParPrenomPuisNip() {
        // on ajoute un homonyme exact sur nom/prénom pour vérifier le 3e critère (NIP)
        Etudiant eBob2 = new Etudiant(new Identite("N000", "Bernard", "Bob"), infoL3);
        g.ajouterEtudiant(eBob2);

        List<Etudiant> tri = g.triAlpha();
        // eBob2 (NIP N000) doit venir avant eBob (NIP N002) car NIP sert de briseur d'égalité
        int idxBob2 = tri.indexOf(eBob2);
        int idxBob  = tri.indexOf(eBob);
        assertTrue(idxBob2 > -1 && idxBob > -1 && idxBob2 < idxBob);
    }
}

