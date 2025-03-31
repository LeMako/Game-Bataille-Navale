package controleur;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modele.Grille;
import modele.Case;
import vue.VuePanneauJeu;

/**
 * Le Contrôleur : gère les interactions utilisateur (clics souris)
 * et met à jour le Modèle (Grille) et la Vue (VuePanneauJeu).
 */
public class ControleurJeu extends MouseAdapter {

    private Grille grilleHumain;
    private Grille grilleOrdinateur;
    private VuePanneauJeu vuePanneau;

    /**
     * Construit le contrôleur du jeu.
     * @param grilleHumain Le modèle de la grille du joueur humain.
     * @param grilleOrdinateur Le modèle de la grille de l'ordinateur.
     * @param vuePanneau La vue à mettre à jour et à interroger pour les positions.
     */
    public ControleurJeu(Grille grilleHumain, Grille grilleOrdinateur, VuePanneauJeu vuePanneau) {
        this.grilleHumain = grilleHumain;
        this.grilleOrdinateur = grilleOrdinateur;
        this.vuePanneau = vuePanneau;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Coordonnées du clic souris dans le panneau
        int clicX = e.getX();
        int clicY = e.getY();

        // Récupération des informations de positionnement depuis la Vue
        int tailleCasePx = vuePanneau.getTailleCasePx();
        int grilleOrdiX = vuePanneau.getOrdiGridX();
        int grilleOrdiY = vuePanneau.getOrdiGridY();
        int tailleGrille = grilleOrdinateur.getTaille(); // Taille logique (ex: 10)
        int tailleGrillePx = tailleGrille * tailleCasePx; // Taille graphique en pixels

        // Vérifier si le clic est DANS la zone de la grille de l'ordinateur
        if (clicX >= grilleOrdiX && clicX < grilleOrdiX + tailleGrillePx &&
            clicY >= grilleOrdiY && clicY < grilleOrdiY + tailleGrillePx) {

            // Calculer les indices (i, j) de la case cliquée dans la grille logique
            int i = (clicX - grilleOrdiX) / tailleCasePx;
            int j = (clicY - grilleOrdiY) / tailleCasePx;
            Point pointCible = new Point(i, j);

            // Vérifier si la case cible existe et n'a pas déjà été touchée
            Case caseVisee = grilleOrdinateur.getCase(pointCible);
            if (caseVisee != null && !caseVisee.estTouchee()) {
                // C'est une cible valide et non touchée : le joueur tire
                System.out.println("Joueur cible : (" + pointCible.x + ", " + pointCible.y + ")");
                grilleOrdinateur.tirer(pointCible); // Appel au modèle pour effectuer le tir

                // Demander à la vue de se redessiner pour (potentiellement) montrer le résultat
                vuePanneau.repaint();

            } else if (caseVisee != null && caseVisee.estTouchee()) {
                // Informer que la case est déjà jouée
                System.out.println("Case ("+ i + "," + j +") déjà visée !");
            }

        }
    }
}