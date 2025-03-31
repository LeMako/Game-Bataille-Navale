package controleur;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
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
    private Random random; // Générateur pour l'IA

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
        this.random = new Random(); // Initialisation du générateur
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
        int tailleGrille = grilleOrdinateur.getTaille();
        int tailleGrillePx = tailleGrille * tailleCasePx;

        // Vérifier si le clic est DANS la zone de la grille de l'ordinateur
        if (clicX >= grilleOrdiX && clicX < grilleOrdiX + tailleGrillePx &&
            clicY >= grilleOrdiY && clicY < grilleOrdiY + tailleGrillePx) {

            // Calculer les indices (i, j) de la case cliquée
            int i = (clicX - grilleOrdiX) / tailleCasePx;
            int j = (clicY - grilleOrdiY) / tailleCasePx;
            Point pointCible = new Point(i, j);

            // Vérifier si la case cible existe et n'a pas déjà été touchée
            Case caseVisee = grilleOrdinateur.getCase(pointCible);
            if (caseVisee != null && !caseVisee.estTouchee()) {

                // Tour du Joueur
                System.out.println("Joueur cible : (" + pointCible.x + ", " + pointCible.y + ")");
                grilleOrdinateur.tirer(pointCible);
                vuePanneau.repaint();

                // Tour de l'Ordinateur
                System.out.println("--- Tour de l'Ordinateur ---");
                grilleHumain.tirOrdinateur(random); // L'ordinateur tire sur la grille humaine
                vuePanneau.repaint(); // Redessine après le tir de l'ordinateur

            } else if (caseVisee != null && caseVisee.estTouchee()) {
                System.out.println("Case ("+ i + "," + j +") déjà visée !");
            }

        }
    }
}