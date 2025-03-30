package src;

import modele.Grille;
import modele.Navire;  
import java.awt.Point;

public class Demo {

    public static void main(String[] args) {
        System.out.println("Lancement Bataille Navale (Placement)");

        final int TAILLE_GRILLE = 10;

        // Création du Modèle
        Grille grilleJoueur = new Grille(TAILLE_GRILLE, true);
        // Grille grilleOrdinateur = new Grille(TAILLE_GRILLE, false);

        System.out.println("\n--- Tentative de placement sur grille Joueur ---");

        // Création de quelques navires
        Navire porteAvion = new Navire(5);
        Navire croiseur = new Navire(4);
        Navire contreTorpilleur = new Navire(3);
        Navire torpilleur = new Navire(2);

        // Placement fixe (pour tester la logique placerNavire)
        boolean p1 = grilleJoueur.placerNavire(porteAvion, new Point(0, 0), false); // Horizontal A1-E1
        boolean p2 = grilleJoueur.placerNavire(croiseur, new Point(0, 2), true);   // Vertical A3-A6
        boolean p3 = grilleJoueur.placerNavire(contreTorpilleur, new Point(5, 5), false); // Horizontal F6-H6
        boolean p4 = grilleJoueur.placerNavire(torpilleur, new Point(8, 8), true);   // Vertical I9-I10

        // Tentative de placement invalide (chevauchement)
        System.out.println("\nTentative de placement invalide (chevauchement):");
        Navire autreNavire = new Navire(3);
        boolean p5 = grilleJoueur.placerNavire(autreNavire, new Point(0, 0), true); // Devrait échouer

        // Tentative de placement invalide (hors grille)
        System.out.println("\nTentative de placement invalide (hors grille):");
        boolean p6 = grilleJoueur.placerNavire(autreNavire, new Point(8, 8), false); // Devrait échouer

        System.out.println("\nRésultats placements (true=réussi, false=échoué):");
        System.out.println("P1 (Porte-avion): " + p1);
        System.out.println("P2 (Croiseur): " + p2);
        System.out.println("P3 (Contre-torp.): " + p3);
        System.out.println("P4 (Torpilleur): " + p4);
        System.out.println("P5 (Invalide chev.): " + p5);
        System.out.println("P6 (Invalide hors): " + p6);
    }
}
