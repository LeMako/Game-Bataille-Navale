package modele;

public class Navire {
    private int taille;

    /**
     * Construit un navire avec une taille donnée.
     * @param taille La taille initiale du navire.
     */
    public Navire(int taille) {
        if (taille <= 0) {
             throw new IllegalArgumentException("La taille d'un navire doit être positive.");
        }
        this.taille = taille;
        System.out.println("Création Navire taille " + taille); // Debug initial
    }

    /**
     * Retourne la taille initiale du navire.
     * @return La taille.
     */
    public int getTaille() {
        return this.taille;
    }

}