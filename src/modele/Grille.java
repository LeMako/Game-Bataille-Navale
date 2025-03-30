package modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
                this.cases[x][y] = new Case(new Point(x, y), null);
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
     * Ajout: Vérification des limites.
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @return La Case, ou null si hors grille.
     */
    public Case getCase(int x, int y) {
        if (x >= 0 && x < taille && y >= 0 && y < taille) {
            return this.cases[x][y];
        }
        return null; // Coordonnées invalides
    }

     /**
     * Récupère la Case à une position donnée (Point).
     * @param p Le Point contenant les coordonnées.
     * @return La Case, ou null si invalide.
     */
    public Case getCase(Point p) {
        if (p == null) return null;
        return getCase(p.x, p.y); // Utilise la vérification de l'autre méthode
    }

    /**
     * Indique si c'est la grille du joueur humain.
     * @return boolean
     */
    public boolean estGrilleHumain() {
        return this.estGrilleHumain;
    }

    /**
     * Ajout: Vérifie si un point est dans les limites de la grille.
     * @param point Le point à vérifier.
     * @return true si le point est valide, false sinon.
     */
    public boolean estPointValide(Point point) {
        return point != null && point.x >= 0 && point.x < taille && point.y >= 0 && point.y < taille;
    }

    /**
     * Ajout: Tente de placer un navire sur la grille.
     * Vérifie les limites et les chevauchements.
     * @param navire Le navire à placer.
     * @param pointDepart La case de départ (coin sup/gauche).
     * @param vertical true pour vertical, false pour horizontal.
     * @return true si le placement a réussi, false sinon.
     */
    public boolean placerNavire(Navire navire, Point pointDepart, boolean vertical) {
        if (navire == null || pointDepart == null) return false;

        List<Point> positionsAOccuper = new ArrayList<>();
        Point courant = new Point(pointDepart); // Copie pour itération

        // 1. Vérifier la validité de toutes les positions
        for (int i = 0; i < navire.getTaille(); i++) {
            // Le point est dans la grille?
            if (!estPointValide(courant)) {
                System.out.println("Placement échoué: Hors grille en " + courant);
                return false;
            }
            // La case est déjà occupée ?
            Case c = getCase(courant); // Le point est valide ici
            if (c.getNavire() != null) {
                System.out.println("Placement échoué: Case déjà occupée en " + courant);
                return false;
            }

            positionsAOccuper.add(new Point(courant)); // Ajoute une copie

            // Calculer la position suivante
            if (vertical) {
                courant.y++;
            } else {
                courant.x++;
            }
        }

        // Si toutes les positions sont valides, placer le navire
        System.out.print("Placement réussi pour navire taille " + navire.getTaille() + " en " + pointDepart + (vertical ? " (V)" : " (H)") + " sur cases: ");
        for (Point p : positionsAOccuper) {
            System.out.print("(" + p.x + "," + p.y + ") ");
            getCase(p).setNavire(navire); // Assigne le même objet navire à chaque case
        }
        System.out.println();
        return true; // Placement réussi
    }

}