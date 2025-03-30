// Garde les imports existants de Commit 3
package src;

import modele.Grille;
import modele.Navire;
import java.awt.Point;
import javax.swing.JFrame;
import vue.VuePanneauJeu;


public class Demo extends JFrame {
    // private static final int LARGEUR = 800;
    // private static final int HAUTEUR = 450;

    /**
     * Constructeur de la fenêtre principale.
     * @param panneauJeu Le JPanel contenant la vue du jeu.
     */
    public Demo(VuePanneauJeu panneauJeu) {
        super("Bataille Navale"); // Titre

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quitter l'appli quand on ferme
        //this.setSize(LARGEUR, HAUTEUR);
        this.setResizable(false);         // Empêcher redimensionnement
        this.setLocationRelativeTo(null); // Centrer à l'écran

        this.add(panneauJeu); // Ajoute notre panneau de dessin à la fenêtre
        this.pack(); // Ajuste la taille de la fenêtre au contenu (setPreferredSize du panneau)
    }


    public static void main(String[] args) {
        System.out.println("Lancement Bataille Navale");

        final int TAILLE_GRILLE = 10;

        // Création du Modèle
        Grille grilleJoueur = new Grille(TAILLE_GRILLE, true);
        Grille grilleOrdinateur = new Grille(TAILLE_GRILLE, false);

        // Placement manuel d'un navire pour tester l'affichage
        Navire porteAvion = new Navire(5); // Exe: Porte-avion
        boolean placeOk = grilleJoueur.placerNavire(porteAvion, new Point(1, 1), false); // Horizontal en B2 (indices 1,1)
        if (placeOk) {
            System.out.println("Navire de test placé pour le joueur.");
        } else {
            System.err.println("ERREUR lors du placement du navire de test !");
        }

        // Création de la Vue
        VuePanneauJeu panneauJeu = new VuePanneauJeu(grilleJoueur, grilleOrdinateur);

        // Création et Affichage de la Fenêtre
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 // Instanciation de la fenêtre DANS invokeLater
                Demo fenetreJeu = new Demo(panneauJeu);
                fenetreJeu.setVisible(true); // Rend la fenêtre visible
            }
        });

        System.out.println("Fenêtre lancée.");
    }
}