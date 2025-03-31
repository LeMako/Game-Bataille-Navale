package src;

import modele.Grille;
import modele.Navire;
import java.awt.Point;
import javax.swing.JFrame;
import vue.VuePanneauJeu;
import controleur.ControleurJeu;

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
        // Navires Joueur
        System.out.println("Placement navires joueur...");
        Navire porteAvionJ = new Navire(5);
        grilleJoueur.placerNavire(porteAvionJ, new Point(1, 1), false); // B2 Horizontal
        Navire torpilleurJ = new Navire(2);
        grilleJoueur.placerNavire(torpilleurJ, new Point(5, 5), true); // F6 Vertical

        // Navires Ordinateur (pour que le joueur puisse tirer dessus)
        System.out.println("Placement navires ordinateur...");
        Navire croiseurO = new Navire(4);
        grilleOrdinateur.placerNavire(croiseurO, new Point(3, 4), true); // D5 Vertical
        Navire sousMarinO = new Navire(3);
        grilleOrdinateur.placerNavire(sousMarinO, new Point(8, 1), false); // I2 Horizontal
        Navire contreTorpilleurO = new Navire(3);
        grilleOrdinateur.placerNavire(contreTorpilleurO, new Point(0, 8), false); // A9 Horizontal


        // Création de la Vue
        VuePanneauJeu panneauJeu = new VuePanneauJeu(grilleJoueur, grilleOrdinateur);

        // Création du Contrôleur
        ControleurJeu controleur = new ControleurJeu(grilleJoueur, grilleOrdinateur, panneauJeu);

        // Liaison Vue -> Contrôleur
        panneauJeu.addMouseListener(controleur); // La vue notifie le contrôleur des clics

        // Création et Affichage de la Fenêtre
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 // Instanciation de la fenêtre DANS invokeLater
                Demo fenetreJeu = new Demo(panneauJeu);
                fenetreJeu.setVisible(true); // Rend la fenêtre visible
            }
        });

        System.out.println("Fenêtre lancée. Cliquez sur la grille de droite pour tirer.");
    }
}