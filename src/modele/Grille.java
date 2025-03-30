package modele;

import java.awt.Point;

public class Grille {
    private int taille;
    private Case[][] cases;
    private boolean estGrilleHumain;

    /**
     * Construit une grille de jeu vide.
     * @param taille Dimension de la grille (taille x taille).
     * @param estGrilleHumain true si grille du joueur, false pour ordi.
     */
    public Grille(int taille, boolean estGrilleHumain) {
        if (taille <= 0) {
             throw new IllegalArgumentException("La taille de la grille doit être positive.");
        }
        this.taille = taille;
        this.cases = new Case[taille][taille];
        this.estGrilleHumain = estGrilleHumain;
        initialiserGrille();
        System.out.println("Création Grille taille " + taille + "x" + taille + " pour " + (estGrilleHumain ? "Joueur" : "Ordinateur"));
    }

    /**
     * Initialise la grille avec des objets Case vides.
     */
    public void initialiserGrille() {
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                this.cases[x][y] = new Case(new Point(x, y), null); // Case vide
            }
        }
    }

    /**
     * Retourne la taille de la grille.
     * @return La taille.
     */
    public int getTaille() {
        return this.taille;
    }

    /**
     * Récupère la Case à une position donnée.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @return La Case.
     */
    public Case getCase(int x, int y) {
        return this.cases[x][y];
    }

     /**
     * Récupère la Case à une position donnée (Point).
     * @param p Le Point contenant les coordonnées.
     * @return La Case.
     */
    public Case getCase(Point p) {
        if (p == null) return null;
        // Vérification des limites sera ajoutée plus tard
        return getCase(p.x, p.y);
    }

    /**
     * Indique si c'est la grille du joueur humain.
     * @return boolean
     */
    public boolean estGrilleHumain() {
        return this.estGrilleHumain;
    }
}
