import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Représente un groupe d'étudiants appartenant à la même formation.
 * Un étudiant ne peut être ajouté au groupe que si sa formation est la même
 * que celle du groupe.
 */
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
     * Ajoute un étudiant au groupe si et seulement si sa formation est la même
     * que celle du groupe. Ne fait rien si l'étudiant est déjà membre.
     *
     * @param etudiant étudiant à ajouter (non null)
     * @return true si l'étudiant a été ajouté, false s'il était déjà présent
     * @throws IllegalArgumentException si l'étudiant est null ou si sa formation diffère
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
     * Supprime un étudiant du groupe s'il est présent.
     *
     * @param etudiant étudiant à supprimer
     * @return true si un membre a été supprimé, false sinon
     */
    public boolean supprimerEtudiant(Etudiant etudiant) {
        if (etudiant == null) return false;
        return membres.remove(etudiant);
    }

    /**
     * Renvoie une vue non modifiable des membres du groupe.
     */
    public Set<Etudiant> getMembres() {
        return Collections.unmodifiableSet(membres);
    }

    public int taille() {
        return membres.size();
    }

    public boolean contient(Etudiant e) {
        return membres.contains(e);
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
