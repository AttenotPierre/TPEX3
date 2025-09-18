import java.util.*;

public class Etudiant {
    private Identite identite;
    private Formation formation;
    private Map<Matiere, List<Double>> notes;

    public Etudiant(Identite identite, Formation formation) {
        this.identite = identite;
        this.formation = formation;
        this.notes = new HashMap<>();
    }

    public Identite getIdentite() {
        return identite;
    }

    public Formation getFormation() {
        return formation;
    }

    // Ajouter une note à une matière
    public boolean ajouterNote(Matiere matiere, double note) {
        if (!formation.contientMatiere(matiere)) {
            System.out.println("Erreur : matière non présente dans la formation.");
            return false;
        }
        if (note < 0 || note > 20) {
            System.out.println("Erreur : la note doit être comprise entre 0 et 20.");
            return false;
        }
        notes.computeIfAbsent(matiere, k -> new ArrayList<>()).add(note);
        return true;
    }

    // Calculer la moyenne pour une matière
    public double moyenneMatiere(Matiere matiere) {
        if (!formation.contientMatiere(matiere)) {
            System.out.println("Erreur : matière non présente dans la formation.");
            return -1;
        }
        List<Double> notesMatiere = notes.get(matiere);
        if (notesMatiere == null || notesMatiere.isEmpty()) {
            return 0;
        }
        double somme = 0;
        for (double n : notesMatiere) {
            somme += n;
        }
        return somme / notesMatiere.size();
    }

    // Calculer la moyenne générale pondérée par les coefficients
    public double moyenneGenerale() {
        double somme = 0;
        double totalCoeff = 0;
        for (Matiere matiere : formation.getMatieres()) {
            double coeff = formation.getCoefficient(matiere);
            double moyenne = moyenneMatiere(matiere);
            if (moyenne >= 0) { // -1 si matière absente, 0 si pas de note
                somme += moyenne * coeff;
                totalCoeff += coeff;
            }
        }
        if (totalCoeff == 0) return 0;
        return somme / totalCoeff;
    }
}