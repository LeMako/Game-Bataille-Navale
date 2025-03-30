package src;

import modele.Grille;

public class Demo {

    public static void main(String[] args) {
        System.out.println("Bataille Navale");

        final int TAILLE_GRILLE = 10;

        // Création du Modèle (juste pour tester)
        Grille grilleJoueur = new Grille(TAILLE_GRILLE, true);
        Grille grilleOrdinateur = new Grille(TAILLE_GRILLE, false);

        System.out.println("Grilles créées.");
    }
}
