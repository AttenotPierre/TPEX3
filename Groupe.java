import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * represente un groupe d'etudiants appartenant à la meme formation
 * un etudiant ne peut etre ajoute au groupe que si sa formation n'est la mem
 * */
public class Groupe {

    private final String nom;
    private final Formation formation;
    private final Set<Etudiant> membres = new HashSet<>();

    public Groupe(String nom, Formation formation) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du groupe ne peut pas être vide.");
        }
        if (formation == null) {
            throw new IllegalArgumentException("La formation du groupe ne peut pas être null.");
        }
        this.nom = nom;
        this.formation = formation;
    }

    public String getNom() {
        return nom;
    }

    public Formation getFormation() {
        return formation;
    }

    /**
      ne fait rien si l'etudiant est deja membre
      return true si l'etudiant a ete ajoute, false s'il etait deja present
      throws IllegalArgumentException si l'étudiant est null
      */
    public boolean ajouterEtudiant(Etudiant etudiant) {
        if (etudiant == null) {
            throw new IllegalArgumentException("L'étudiant ne peut pas être null.");
        }
        if (etudiant.getFormation() == null) {
            throw new IllegalArgumentException("La formation de l'étudiant ne peut pas être null.");
        }
        if (!formation.equals(etudiant.getFormation())) {
            throw new IllegalArgumentException(
                    "Impossible d'ajouter l'étudiant : formation différente (groupe="
                            + formation + ", etudiant=" + etudiant.getFormation() + ")."
            );
        }
        return membres.add(etudiant);
    }

    /**
      supprime un etudiant du groupe s'il est présent
      @return true si un membre a ete supprime, sinon false
     */
    public boolean supprimerEtudiant(Etudiant etudiant) {
        if (etudiant == null) return false;
        return membres.remove(etudiant);
    }


     //renvoie une vue non modifiable des membres du groupe

    public Set<Etudiant> getMembres() {
        return Collections.unmodifiableSet(membres);
    }

    public int taille() {
        return membres.size();
    }

    public boolean contient(Etudiant e) {
        return membres.contains(e);
    }

    public double calculerMoyenneMatiere(Matiere matiere) {
        if (matiere == null) {
            throw new IllegalArgumentException("La matière ne peut pas être null.");
        }
        if (membres.isEmpty()) return 0.0;

        double somme = 0.0;
        int compteur = 0;

        for (Etudiant e : membres) {
            double moyenne = e.moyenneMatiere(matiere);
            if (moyenne >= 0) { // -1 si matière absente
                somme += moyenne;
                compteur++;
            }
        }
        return (compteur == 0) ? 0.0 : Math.round((somme / compteur) * 10.0) / 10.0;
    }

    public double calculerMoyenneGenerale() {
        if (membres.isEmpty()) return 0.0;

        double somme = 0.0;
        int compteur = 0;

        for (Etudiant e : membres) {
            double moyenne = e.moyenneGenerale();
            somme += moyenne;
            compteur++;
        }
        return (compteur == 0) ? 0.0 : Math.round((somme / compteur) * 10.0) / 10.0;
    }
    public List<Etudiant> triAlpha() {
        List<Etudiant> res = new ArrayList<>(membres);
        res.sort(Comparator
                .comparing((Etudiant e) -> e.getIdentite().getNom(), String.CASE_INSENSITIVE_ORDER)
                .thenComparing(e -> e.getIdentite().getPrenom(), String.CASE_INSENSITIVE_ORDER)
                .thenComparing(e -> e.getIdentite().getNip()));
        return res;
    }

    public List<Etudiant> triAntiAlpha() {
        List<Etudiant> res = triAlpha();
        Collections.reverse(res);
        return res;
    }

    public List<Etudiant> triParMerite() {
        List<Etudiant> sorted = new ArrayList<>(membres);
        sorted.sort(Comparator.comparingDouble(Etudiant::moyenneGenerale).reversed());
        return sorted;
    }


    @Override
    public String toString() {
        return "Groupe{" +
                "nom='" + nom + '\'' +
                ", formation=" + formation +
                ", membres=" + membres.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Groupe)) return false;
        Groupe groupe = (Groupe) o;
        // Un groupe est identifié par son nom et sa formation
        return nom.equals(groupe.nom) && formation.equals(groupe.formation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, formation);
    }
}
