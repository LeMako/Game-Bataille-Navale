package modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Grille {
    private int taille;
    private Case[][] cases;
    private boolean estGrilleHumain;
    // Ajouté: Compteurs pour les tirs
    private int score; // Nombre de tirs réussis (touché un bateau adverse sur CETTE grille)
    private int tirsManques; // Nombre de tirs dans l'eau (ratés) sur CETTE grille

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
        this.score = 0; // Initialisation
        this.tirsManques = 0; // Initialisation
        initialiserGrille();
        // System.out.println("Création Grille taille " + taille + "x" + taille + " pour " + (estGrilleHumain ? "Joueur" : "Ordinateur"));
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
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     * @return La Case, ou null si hors grille.
     */
    public Case getCase(int x, int y) {
        if (x >= 0 && x < taille && y >= 0 && y < taille) {
            return this.cases[x][y];
        }
        return null;
    }

     /**
     * Récupère la Case à une position donnée (Point).
     * @param p Le Point contenant les coordonnées.
     * @return La Case, ou null si invalide.
     */
    public Case getCase(Point p) {
        if (p == null) return null;
        return getCase(p.x, p.y);
    }

    /**
     * Indique si c'est la grille du joueur humain.
     * @return boolean
     */
    public boolean estGrilleHumain() {
        return this.estGrilleHumain;
    }

    /**
     * Vérifie si un point est dans les limites de la grille.
     * @param point Le point à vérifier.
     * @return true si le point est valide, false sinon.
     */
    public boolean estPointValide(Point point) {
        return point != null && point.x >= 0 && point.x < taille && point.y >= 0 && point.y < taille;
    }

    /**
     * Ajouté: Retourne le score actuel sur cette grille (nombre de tirs adverses ayant touché un navire).
     * @return Le score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Ajouté: Retourne le nombre de tirs adverses manqués (dans l'eau) sur cette grille.
     * @return Le nombre de tirs manqués.
     */
    public int getTirsManques() {
        return this.tirsManques;
    }


    /**
     * Tente de placer un navire sur la grille.
     * Vérifie les limites et les chevauchements.
     * @param navire Le navire à placer.
     * @param pointDepart La case de départ (coin sup/gauche).
     * @param vertical true pour vertical, false pour horizontal.
     * @return true si le placement a réussi, false sinon.
     */
    public boolean placerNavire(Navire navire, Point pointDepart, boolean vertical) {
        if (navire == null || pointDepart == null) return false;

        List<Point> positionsAOccuper = new ArrayList<>();
        Point courant = new Point(pointDepart);

        // 1. Vérifier la validité de toutes les positions
        for (int i = 0; i < navire.getTaille(); i++) {
            if (!estPointValide(courant)) {
                System.err.println("Placement échoué: Hors grille en " + courant + " pour navire taille " + navire.getTaille());
                return false;
            }
            Case c = getCase(courant);
            if (c.getNavire() != null) {
                System.err.println("Placement échoué: Case déjà occupée en " + courant + " pour navire taille " + navire.getTaille());
                return false;
            }
            positionsAOccuper.add(new Point(courant));

            if (vertical) {
                courant.y++;
            } else {
                courant.x++;
            }
        }

        // 2. Si toutes les positions sont valides, placer le navire
        // System.out.print("Placement réussi pour navire taille " + navire.getTaille() + " en " + pointDepart + (vertical ? " (V)" : " (H)") + " sur cases: ");
        for (Point p : positionsAOccuper) {
           // System.out.print("(" + p.x + "," + p.y + ") ");
            getCase(p).setNavire(navire);
        }
        // System.out.println();
        return true;
    }


    /**
     * Ajouté: Enregistre un tir à une position donnée sur CETTE grille.
     * Met à jour le score ou les tirs manqués, marque la case comme touchée,
     * et réduit la vie du navire si touché.
     * @param point La position (Point) du tir.
     * @return true si un navire est touché, false si le tir est manqué, sur une case déjà visée, ou invalide.
     */
    public boolean tirer(Point point) {
        if (!estPointValide(point)) {
            System.err.println("Tir hors grille : " + point);
            return false; // Tir hors grille
        }

        Case caseVisee = getCase(point); // Récupère la case ciblée (ne peut pas être null ici)

        // Vérifie si la case a déjà été touchée
        if (caseVisee.estTouchee()) {
            System.out.println("Case (" + point.x + "," + point.y + ") déjà visée."); // Message utilisateur
            return false; // Case déjà visée, on ne fait rien de plus
        }

        // Marque la case comme touchée, qu'il y ait un navire ou non
        caseVisee.recevoirTir();

        Navire navireTouche = caseVisee.getNavire(); // Récupère le navire sur la case (peut être null)

        if (navireTouche != null) {
            // Il y a un navire sur la case touchée
            navireTouche.recevoirTir(); // Réduit la vie du navire
            this.score++; // Augmente le score (nombre de touches sur cette grille)
            System.out.println(" -> Touché ! Navire en (" + point.x + "," + point.y + "). Points restants: " + navireTouche.getTaille());
            if (navireTouche.estCoule()) {
                System.out.println("   -> Navire coulé !");
            }
            return true; // Touché !
        } else {
            // Pas de navire sur la case touchée
            this.tirsManques++; // Augmente le compteur de tirs manqués
            System.out.println(" -> Manqué (dans l'eau) en (" + point.x + "," + point.y + ").");
            return false; // Manqué ! (tir dans l'eau)
        }
    }

}