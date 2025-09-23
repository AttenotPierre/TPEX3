import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Formation {
    private String identifiant;
    private Map<String, Integer> matieresCoefficients;

    public Formation(String identifiant) {
        this.identifiant = identifiant;
        this.matieresCoefficients = new HashMap<>();
    }

    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * Ajoute une matière avec son coefficient à la formation
     * @param matiere nom de la matière
     * @param coefficient coefficient de la matière (doit être > 0)
     */
    public void ajouterMatiere(String matiere, int coefficient) {
        if (coefficient <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être supérieur à 0");
        }
        matieresCoefficients.put(matiere, coefficient);
    }

    /**
     * Supprime une matière de la formation
     * @param matiere nom de la matière à supprimer
     * @return true si la matière a été supprimée, false si elle n'existait pas
     */
    public boolean supprimerMatiere(String matiere) {
        return matieresCoefficients.remove(matiere) != null;
    }

    /**
     * Retourne le coefficient d'une matière
     * @param matiere nom de la matière
     * @return coefficient de la matière
     * @throws IllegalArgumentException si la matière n'est pas dans la formation
     */
    public int getCoefficientMatiere(String matiere) {
        Integer coefficient = matieresCoefficients.get(matiere);
        if (coefficient == null) {
            throw new IllegalArgumentException("La matière '" + matiere + "' n'existe pas dans cette formation");
        }
        return coefficient;
    }

    /**
     * Vérifie si une matière existe dans la formation
     * @param matiere nom de la matière
     * @return true si la matière existe, false sinon
     */
    public boolean contientMatiere(String matiere) {
        return matieresCoefficients.containsKey(matiere);
    }

    /**
     * Retourne l'ensemble des matières de la formation
     * @return Set des noms de matières
     */
    public Set<String> getMatieres() {
        return matieresCoefficients.keySet();
    }

    /**
     * Retourne la map complète des matières et coefficients
     * @return Map<String, Integer> des matières et leurs coefficients
     */
    public Map<String, Integer> getMatieresCoefficients() {
        return new HashMap<>(matieresCoefficients);
    }

    @Override
    public String toString() {
        return "Formation{" +
                "identifiant='" + identifiant + '\'' +
                ", matieresCoefficients=" + matieresCoefficients +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Formation formation = (Formation) obj;
        return identifiant.equals(formation.identifiant);
    }

    @Override
    public int hashCode() {
        return identifiant.hashCode();
    }
}