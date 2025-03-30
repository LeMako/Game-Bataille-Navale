package modele;

import java.awt.Point;

public class Case {
    private Point point;    // Position (x, y)
    private Navire navire;  // Référence au navire (null si vide)
    private boolean touchee; // Ajouté: Indique si un tir a déjà visé cette case

    /**
     * Crée une case.
     * @param point La position de la case.
     * @param navire Le navire sur la case (peut être null).
     */
    public Case(Point point, Navire navire) {
        this.point = point;
        this.navire = navire;
        this.touchee = false; // Initialisation à false
    }

    /**
     * Renvoie le navire sur cette case.
     * @return Le navire, ou null.
     */
    public Navire getNavire() {
        return this.navire;
    }

    /**
     * Place un navire sur cette case.
     * @param navire Le navire à placer.
     */
    public void setNavire(Navire navire) {
        this.navire = navire;
    }

     /**
     * Renvoie la position de cette case.
     * @return La position.
     */
    public Point getPoint() {
        return this.point;
    }

    /**
     * Ajouté: Vérifie si cette case a déjà été la cible d'un tir.
     * @return true si la case a été touchée, false sinon.
     */
    public boolean estTouchee() {
        return this.touchee;
    }

    /**
     * Ajouté: Marque cette case comme ayant été touchée par un tir.
     */
    public void recevoirTir() {
        this.touchee = true;
    }
}