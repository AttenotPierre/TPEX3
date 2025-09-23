import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Formation {
    private String identifiant;
    private Map<Matiere, Integer> matieresCoefficients; // Matiere -> coefficient

    public Formation(String identifiant) {
        this.identifiant = identifiant;
        this.matieresCoefficients = new HashMap<>();
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void ajouterMatiere(Matiere matiere, int coefficient) {
        if (coefficient <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être supérieur à 0");
        }
        matieresCoefficients.put(matiere, coefficient);
    }

    public boolean supprimerMatiere(Matiere matiere) {
        return matieresCoefficients.remove(matiere) != null;
    }

    public int getCoefficient(Matiere matiere) {
        Integer coeff = matieresCoefficients.get(matiere);
        if (coeff == null) {
            throw new IllegalArgumentException("La matière '" + matiere + "' n'existe pas dans cette formation");
        }
        return coeff;
    }

    public boolean contientMatiere(Matiere matiere) {
        return matieresCoefficients.containsKey(matiere);
    }

    public Set<Matiere> getMatieres() {
        return matieresCoefficients.keySet();
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