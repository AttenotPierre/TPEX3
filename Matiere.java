public class Matiere {
    private String nom;

    public Matiere(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matiere matiere = (Matiere) obj;
        return nom.equals(matiere.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}