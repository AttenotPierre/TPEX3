import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class FormationTest {
    private Formation formation;

    @BeforeEach
    void setUp() {
        formation = new Formation("Informatique L3");
    }

    @Test
    @DisplayName("Test du constructeur et identifiant")
    void testConstructeur() {
        assertEquals("Informatique L3", formation.getIdentifiant());
        assertTrue(formation.getMatieres().isEmpty());
    }

    @Test
    @DisplayName("Test ajout de matière valide")
    void testAjouterMatiereValide() {
        assertDoesNotThrow(() -> {
            formation.ajouterMatiere("Programmation", 3);
        });

        assertTrue(formation.contientMatiere("Programmation"));
        assertEquals(3, formation.getCoefficientMatiere("Programmation"));
        assertEquals(1, formation.getMatieres().size());
    }

    @Test
    @DisplayName("Test ajout de matière avec coefficient invalide")
    void testAjouterMatiereCoeffInvalide() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            formation.ajouterMatiere("Programmation", 0);
        });
        assertEquals("Le coefficient doit être supérieur à 0", exception.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            formation.ajouterMatiere("Mathématiques", -1);
        });
        assertEquals("Le coefficient doit être supérieur à 0", exception2.getMessage());
    }

    @Test
    @DisplayName("Test ajout de plusieurs matières")
    void testAjouterPlusieursMatières() {
        formation.ajouterMatiere("Programmation", 3);
        formation.ajouterMatiere("Mathématiques", 2);
        formation.ajouterMatiere("Anglais", 1);

        assertEquals(3, formation.getMatieres().size());
        assertTrue(formation.contientMatiere("Programmation"));
        assertTrue(formation.contientMatiere("Mathématiques"));
        assertTrue(formation.contientMatiere("Anglais"));
    }

    @Test
    @DisplayName("Test modification du coefficient d'une matière existante")
    void testModifierCoefficientMatiere() {
        formation.ajouterMatiere("Programmation", 3);
        assertEquals(3, formation.getCoefficientMatiere("Programmation"));

        // Ajouter à nouveau la même matière avec un coefficient différent
        formation.ajouterMatiere("Programmation", 4);
        assertEquals(4, formation.getCoefficientMatiere("Programmation"));
        assertEquals(1, formation.getMatieres().size()); // Toujours qu'une seule matière
    }

    @Test
    @DisplayName("Test suppression de matière existante")
    void testSupprimerMatiereExistante() {
        formation.ajouterMatiere("Programmation", 3);
        formation.ajouterMatiere("Mathématiques", 2);

        assertTrue(formation.supprimerMatiere("Programmation"));
        assertFalse(formation.contientMatiere("Programmation"));
        assertEquals(1, formation.getMatieres().size());
        assertTrue(formation.contientMatiere("Mathématiques"));
    }

    @Test
    @DisplayName("Test suppression de matière inexistante")
    void testSupprimerMatiereInexistante() {
        formation.ajouterMatiere("Programmation", 3);

        assertFalse(formation.supprimerMatiere("Physique"));
        assertEquals(1, formation.getMatieres().size());
        assertTrue(formation.contientMatiere("Programmation"));
    }

    @Test
    @DisplayName("Test getCoefficientMatiere avec matière existante")
    void testGetCoefficientMatiereExistante() {
        formation.ajouterMatiere("Programmation", 3);
        formation.ajouterMatiere("Mathématiques", 2);

        assertEquals(3, formation.getCoefficientMatiere("Programmation"));
        assertEquals(2, formation.getCoefficientMatiere("Mathématiques"));
    }

    @Test
    @DisplayName("Test getCoefficientMatiere avec matière inexistante")
    void testGetCoefficientMatiereInexistante() {
        formation.ajouterMatiere("Programmation", 3);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            formation.getCoefficientMatiere("Physique");
        });
        assertEquals("La matière 'Physique' n'existe pas dans cette formation", exception.getMessage());
    }

    @Test
    @DisplayName("Test contientMatiere")
    void testContientMatiere() {
        assertFalse(formation.contientMatiere("Programmation"));

        formation.ajouterMatiere("Programmation", 3);
        assertTrue(formation.contientMatiere("Programmation"));
        assertFalse(formation.contientMatiere("Physique"));
    }

    @Test
    @DisplayName("Test getMatieres retourne un Set non modifiable")
    void testGetMatieresIsolation() {
        formation.ajouterMatiere("Programmation", 3);
        formation.ajouterMatiere("Mathématiques", 2);

        Set<String> matieres = formation.getMatieres();
        assertEquals(2, matieres.size());
        assertTrue(matieres.contains("Programmation"));
        assertTrue(matieres.contains("Mathématiques"));

        // Vérification que le Set retourné reflète l'état actuel
        formation.ajouterMatiere("Anglais", 1);
        Set<String> matieresApresMajout = formation.getMatieres();
        assertEquals(3, matieresApresMajout.size());
        assertTrue(matieresApresMajout.contains("Anglais"));
    }

    @Test
    @DisplayName("Test getMatieresCoefficients")
    void testGetMatieresCoefficients() {
        formation.ajouterMatiere("Programmation", 3);
        formation.ajouterMatiere("Mathématiques", 2);

        var matieresCoeff = formation.getMatieresCoefficients();
        assertEquals(2, matieresCoeff.size());
        assertEquals(3, matieresCoeff.get("Programmation"));
        assertEquals(2, matieresCoeff.get("Mathématiques"));

        // Vérifier que c'est une copie (isolation)
        matieresCoeff.put("Physique", 1);
        assertFalse(formation.contientMatiere("Physique"));
    }

    @Test
    @DisplayName("Test égalité entre formations")
    void testEgalite() {
        Formation formation2 = new Formation("Informatique L3");
        Formation formation3 = new Formation("Mathématiques L2");

        assertEquals(formation, formation2);
        assertEquals(formation.hashCode(), formation2.hashCode());
        assertNotEquals(formation, formation3);

        // L'égalité dépend uniquement de l'identifiant, pas des matières
        formation.ajouterMatiere("Programmation", 3);
        assertEquals(formation, formation2);
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        formation.ajouterMatiere("Programmation", 3);
        formation.ajouterMatiere("Mathématiques", 2);

        String result = formation.toString();
        assertTrue(result.contains("Informatique L3"));
        assertTrue(result.contains("Programmation"));
        assertTrue(result.contains("Mathématiques"));
    }

    @Test
    @DisplayName("Test formation vide")
    void testFormationVide() {
        assertTrue(formation.getMatieres().isEmpty());
        assertEquals(0, formation.getMatieresCoefficients().size());
        assertFalse(formation.contientMatiere(""));
        assertFalse(formation.supprimerMatiere("Inexistante"));
    }
}