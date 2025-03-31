package vue;

import java.awt.*;
import javax.swing.*;
import modele.Grille;
import modele.Case;
import modele.Navire;

public class VuePanneauJeu extends JPanel {

    private Grille grilleHumain;
    private Grille grilleOrdinateur;

    // Pour le dessin
    private static final int TAILLE_CASE_PX = 30; // Taille d'une case en pixels
    private static final int MARGE = 50;         // Marge autour des grilles
    private static final int ESPACE_GRILLES = 40; // Espace entre les deux grilles

    // Positions calculées
    private final int tailleGrilleLogique; // Taille logique
    private final int tailleGrillePx;      // Taille en pixels
    private final int grilleHumainX = MARGE;
    private final int grilleHumainY = MARGE;
    private final int grilleOrdiX; // Calculée dans le constructeur
    private final int grilleOrdiY = MARGE;

    // Couleurs
    private Color couleurFond = Color.BLACK;
    private Color couleurGrille = Color.DARK_GRAY;
    private Color couleurBordure = Color.GRAY;
    private Color couleurNavireHumain = Color.BLUE; // Couleur pour les navires du joueur

    /**
     * Construit le panneau d'affichage.
     * @param grilleHumain La grille du joueur humain.
     * @param grilleOrdinateur La grille de l'ordinateur.
     */
    public VuePanneauJeu(Grille grilleHumain, Grille grilleOrdinateur) {
        this.grilleHumain = grilleHumain;
        this.grilleOrdinateur = grilleOrdinateur;

        // Les deux grilles ont la même taille
        this.tailleGrilleLogique = grilleHumain.getTaille();
        this.tailleGrillePx = this.tailleGrilleLogique * TAILLE_CASE_PX;

        // Calcul position X grille ordinateur
        this.grilleOrdiX = grilleHumainX + tailleGrillePx + ESPACE_GRILLES;

        // Le layout de la JFrame
        int largeurPreferee = grilleOrdiX + tailleGrillePx + MARGE;
        // Hauteur prend en compte la marge en bas aussi
        int hauteurPreferee = grilleHumainY + tailleGrillePx + MARGE;
        this.setPreferredSize(new Dimension(largeurPreferee, hauteurPreferee));

        this.setBackground(couleurFond);
        this.setFocusable(true);
    }

    /**
     * Méthode principale de dessin, appelée par Swing.
     * @param g L'objet Graphics fourni par le système.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Utilisation de Graphics2D pour potentiellement améliorer le rendu
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Optionnel

        // Appel à la méthode qui dessine les deux grilles
        dessinerGrilles(g2d);
    }

     /**
     * Méthode principale pour dessiner les deux grilles.
     * @param g L'objet Graphics2D.
     */
    private void dessinerGrilles(Graphics2D g) {
        // Dessiner la grille du joueur
        dessinerGrille(g, grilleHumain, grilleHumainX, grilleHumainY);

        // Dessiner la grille de l'ordinateur
        dessinerGrille(g, grilleOrdinateur, grilleOrdiX, grilleOrdiY);
    }


    /**
     * Dessine UNE grille complète
     * @param g Graphics2D
     * @param grille La grille à dessiner (modèle).
     * @param x Position X du coin supérieur gauche de la grille.
     * @param y Position Y du coin supérieur gauche de la grille.
     */
    private void dessinerGrille(Graphics2D g, Grille grille, int x, int y) {
        boolean estHumain = grille.estGrilleHumain();

        for (int i = 0; i < tailleGrilleLogique; i++) {
            for (int j = 0; j < tailleGrilleLogique; j++) {
                Case caseCourante = grille.getCase(i, j); // Récupère la case logique
                if (caseCourante == null) continue; // Sécurité

                Navire navire = caseCourante.getNavire(); // Récupère le navire sur la case
                int caseX = x + i * TAILLE_CASE_PX;    // Position pixel X de la case
                int caseY = y + j * TAILLE_CASE_PX;    // Position pixel Y de la case

                // 1. Déterminer et dessiner le fond de la case
                Color couleurFondCase = couleurGrille; // Fond par défaut
                if (navire != null && estHumain) {
                    // Si c'est la grille humaine ET qu'il y a un navire, on le dessine
                    couleurFondCase = couleurNavireHumain;
                }
                // (Note : On ne dessine pas encore les navires ennemis cachés)
                dessinerRectanglePlein(g, couleurFondCase, caseX, caseY);

                // 2. Dessiner la bordure de la case
                dessinerContourCase(g, couleurBordure, caseX, caseY);
            }
        }
    }

    /**
     * Dessine le contour d'une case.
     * @param g Graphics
     * @param couleur Couleur de la bordure
     * @param x Coin supérieur gauche X
     * @param y Coin supérieur gauche Y
     */
    private void dessinerContourCase(Graphics g, Color couleur, int x, int y) {
        g.setColor(couleur);
        g.drawRect(x, y, TAILLE_CASE_PX, TAILLE_CASE_PX);
    }

    /**
     * Dessine un rectangle plein représentant le fond d'une case.
     * @param g Graphics
     * @param couleur Couleur de fond
     * @param x Coin supérieur gauche X
     * @param y Coin supérieur gauche Y
     */
    private void dessinerRectanglePlein(Graphics g, Color couleur, int x, int y) {
        g.setColor(couleur);
        g.fillRect(x, y, TAILLE_CASE_PX, TAILLE_CASE_PX);
    }

    // Ajouté: Accesseurs nécessaires pour le Contrôleur
    /** @return Coordonnée X du coin supérieur gauche de la grille ordinateur. */
    public int getOrdiGridX() { 
        return grilleOrdiX; 
    }
    /** @return Coordonnée Y du coin supérieur gauche de la grille ordinateur. */
    public int getOrdiGridY() { 
        return grilleOrdiY; 
    }
    /** @return La taille d'une case en pixels. */
    public int getTailleCasePx() { 
        return TAILLE_CASE_PX; 
    }
}