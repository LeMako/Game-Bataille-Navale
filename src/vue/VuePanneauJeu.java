package vue;

import java.awt.*;
import javax.swing.*; 

import modele.Grille;

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
        // Graphics2D g2d = (Graphics2D) g;

        // Dessiner les deux zones de grille
        dessinerGrilleVide(g, grilleHumainX, grilleHumainY);
        dessinerGrilleVide(g, grilleOrdiX, grilleOrdiY);
    }

    /**
     * Dessine le fond et les lignes d'une grille vide.
     * @param g Graphics
     * @param x Position X du coin supérieur gauche de la grille.
     * @param y Position Y du coin supérieur gauche de la grille.
     */
    private void dessinerGrilleVide(Graphics g, int x, int y) {
        // Dessine le fond de la zone de grille
        g.setColor(couleurGrille);
        g.fillRect(x, y, tailleGrillePx, tailleGrillePx);

        // Dessine les lignes internes (bordures des cases)
        for (int i = 0; i <= tailleGrilleLogique; i++) {
            // Lignes verticales
             dessinerLigne(g, couleurBordure, x + i * TAILLE_CASE_PX, y, x + i * TAILLE_CASE_PX, y + tailleGrillePx);
            // Lignes horizontales
             dessinerLigne(g, couleurBordure, x, y + i * TAILLE_CASE_PX, x + tailleGrillePx, y + i * TAILLE_CASE_PX);
        }
    }

    /**
     * Méthode d'aide pour dessiner une ligne.
     * @param g Graphics
     * @param couleur Couleur de la ligne
     * @param x1 Point de départ x
     * @param y1 Point de départ y
     * @param x2 Point d'arrivée x
     * @param y2 Point d'arrivée y
     */
    private void dessinerLigne(Graphics g, Color couleur, int x1, int y1, int x2, int y2) {
        g.setColor(couleur);
        g.drawLine(x1, y1, x2, y2);
    }
}