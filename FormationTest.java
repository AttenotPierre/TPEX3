import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FormationTest {

    private Formation formation;
    private Matiere prog;
    private Matiere maths;
    private Matiere physique;

    @BeforeEach
    void setUp() {
        formation = new Formation("INFO1");
        prog = new Matiere("Programmation");
        maths = new Matiere("Maths");
        physique = new Matiere("Physique");
    }

    @Test
    void testAjouterEtRecupererCoefficient() {
        formation.ajouterMatiere(prog, 3);
        formation.ajouterMatiere(maths, 2);

        assertTrue(formation.contientMatiere(prog));
        assertTrue(formation.contientMatiere(maths));
        assertEquals(3, formation.getCoefficient(prog));
        assertEquals(2, formation.getCoefficient(maths));
    }

    @Test
    void testAjouterMatiereCoefficientInvalide() {
        assertThrows(IllegalArgumentException.class, () -> formation.ajouterMatiere(prog, 0));
        assertThrows(IllegalArgumentException.class, () -> formation.ajouterMatiere(maths, -1));
        // Rien n'a été ajouté
        assertFalse(formation.contientMatiere(prog));
        assertFalse(formation.contientMatiere(maths));
    }




    @Test
    void testGetCoefficientMatiereInexistante() {
        // prog n'est pas ajouté → doit lever une exception
        assertThrows(IllegalArgumentException.class, () -> formation.getCoefficient(prog));
    }







    @Test
    void testSupprimerMatiere() {
        formation.ajouterMatiere(prog, 3);
        formation.ajouterMatiere(maths, 2);

        assertTrue(formation.supprimerMatiere(prog));
        assertFalse(formation.contientMatiere(prog));

        // Supprimer une matière absente retourne false
        assertFalse(formation.supprimerMatiere(physique));
    }

    @Test
    void testGetMatieresTaille() {
        formation.ajouterMatiere(maths, 2);
        formation.ajouterMatiere(physique, 1);

        Set<Matiere> matieres = formation.getMatieres();
        assertEquals(2, matieres.size());
        assertTrue(matieres.contains(maths));
        assertTrue(matieres.contains(physique));
    }



    @Test
    void testEqualsEtHashCodeSurIdentifiant() {
        Formation f1 = new Formation("INFO1");
        Formation f2 = new Formation("INFO1");
        Formation f3 = new Formation("INFO2");

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1, f3);
    }
}
