import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {
    private Etudiant etudiant;
    private Matiere math;
    private Matiere info;
    private Matiere anglais;
    private Formation formation;

    @BeforeEach
    void Init() {
        math = new Matiere("Mathématiques");
        info = new Matiere("Informatique");
        anglais = new Matiere("Anglais");
        formation = new Formation("BUT Info");
        formation.ajouterMatiere(math, 3);
        formation.ajouterMatiere(info, 4);
        formation.ajouterMatiere(anglais, 2);

        Identite id = new Identite("12345", "Dupont", "Alice");
        etudiant = new Etudiant(id, formation);
    }

    @Test
    void testAjouterNoteValide() {
        assertTrue(etudiant.ajouterNote(math, 15));
        assertTrue(etudiant.ajouterNote(info, 18));
    }

    @Test
    void testAjouterNoteInvalide() {
        assertFalse(etudiant.ajouterNote(math, 25)); // note > 20
        assertFalse(etudiant.ajouterNote(math, -2)); // note < 0
        Matiere sport = new Matiere("Sport");
        assertFalse(etudiant.ajouterNote(sport, 12)); // matière non présente
    }

    @Test
    void testMoyenneMatiere() {
        etudiant.ajouterNote(math, 10);
        etudiant.ajouterNote(math, 14);
        assertEquals(12.0, etudiant.moyenneMatiere(math), 0.01);
    }



    @Test
    void testMoyenneMatiereSansNote() {
        assertEquals(0.0, etudiant.moyenneMatiere(info), 0.01);
    }



    @Test
    void testMoyenneMatiereNonPresente() {
        Matiere sport = new Matiere("Sport");
        assertEquals(-1.0, etudiant.moyenneMatiere(sport), 0.01);
    }




    @Test
    void testMoyenneGenerale() {
        etudiant.ajouterNote(math, 10); // coeff 3
        etudiant.ajouterNote(info, 14); // coeff 4
        etudiant.ajouterNote(anglais, 16); // coeff 2
        double expected = ((10*3) + (14*4) + (16*2)) / (3+4+2);
        assertEquals(expected, etudiant.moyenneGenerale(), 0.1);
    }
}