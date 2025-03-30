package modele;

public class Navire {
    private int taille; // Représente la taille/points de vie du navire

    /**
     * Construit un navire avec une taille donnée.
     * @param taille La taille initiale du navire.
     */
    public Navire(int taille) {
        if (taille <= 0) {
             throw new IllegalArgumentException("La taille d'un navire doit être positive.");
        }
        this.taille = taille;
        // System.out.println("Création Navire taille " + taille); // Message de debug initial peut être retiré si désiré
    }

    /**
     * Retourne la taille actuelle (points de vie restants) du navire.
     * @return La taille actuelle. Vaut 0 si le navire est coulé.
     */
    public int getTaille() {
        return this.taille;
    }

    /**
     * Ajouté: Méthode appelée lorsqu'une partie du navire est touchée.
     * Réduit la taille de 1 si le navire n'est pas déjà coulé.
     */
    public void recevoirTir() {
        if (!this.estCoule()) { // On vérifie si la taille est > 0 avant de décrémenter
            this.taille--;
        }
    }

    /**
     * Ajouté: Vérifie si le navire est coulé.
     * @return true si la taille est 0, false sinon.
     */
    public boolean estCoule() {
        return this.taille <= 0; // Utilise <= par sécurité, bien que == 0 soit le cas normal
    }
}