/*
 * Fichier : Utilisateur.java
 * (Classe principale corrigée pour inclure toutes les options)
 */
package Vue;

import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Consomation;
import Model.CoutRxElct;
import Model.Generateur;
import Model.Maison;
import Model.ReseauElectrique;

class Utilisateur {

    private static void menuCreation() {
        System.out.println("\n--- MENU CREATION ---");
        System.out.println("1 - Ajouter un generateur.");
        System.out.println("2 - Ajouter une maison.");
        System.out.println("3 - Ajouter une connexion.");
        System.out.println("4 - Supprimer une connexion."); // AJOUT
        System.out.println("5 - Fin creation"); // MODIFIE
    }

    private static void menuCalcul() {
        System.out.println("\n--- MENU CALCUL ---");
        System.out.println("1 - Calculer le coût du réseau électrique actuel ");
        System.out.println("2 - Modifier une connexion.");
        System.out.println("3 - Afficher le reseau");
        System.out.println("4 - Fin");
    }

    // Les méthodes find...ByName() ont été déplacées dans ReseauElectrique
    // pour une meilleure encapsulation.

    public static void main(String []args) {
        int choix = 0;

        // On initialise le réseau électrique
        ReseauElectrique rxe = new ReseauElectrique();

        Scanner clavier = new Scanner(System.in);

        // partie 1
        do {
            try {
                menuCreation();
                System.out.print("Votre choix : ");
                choix = clavier.nextInt();
                clavier.nextLine(); // Consomme le retour à la ligne

                switch(choix) {
                    case 1:
                        // (Inchangé, mais appelle rxe.ajoutGenerateur)
                        System.out.println("• Ajout d'un generateur");
                        System.out.print("  Entrez le nom et la capacite max (ex: G1 60) : ");
                        String lineG = clavier.nextLine().trim();
                        String[] partsG = lineG.split(" ");

                        if (partsG.length != 2) {
                            System.err.println("Format invalide. Deux arguments attendus.");
                            break;
                        }
                        try {
                            String nomG = partsG[0];
                            int capa = Integer.parseInt(partsG[1]);
                            rxe.ajoutGenerateur(new Generateur(nomG, capa)); // La logique est dans rxe
                        } catch (NumberFormatException e) {
                            System.err.println("Capacite invalide. Ce doit etre un nombre.");
                        }
                        break;
                    case 2:
                        // (Inchangé, mais appelle rxe.ajoutMaison)
                        System.out.println("• Ajout d'une maison");
                        System.out.print("  Entrez le nom et la consommation (ex: M1 NORMAL) : ");
                        String lineM = clavier.nextLine().trim();
                        String[] partsM = lineM.split(" ");

                        if (partsM.length != 2) {
                            System.err.println("Format invalide. Deux arguments attendus.");
                            break;
                        }
                        try {
                            String nomM = partsM[0];
                            String consoStr = partsM[1].toUpperCase();
                            Consomation consoEnum = Consomation.valueOf(consoStr);
                            rxe.ajoutMaison(new Maison(nomM, consoEnum)); // La logique est dans rxe
                        } catch (IllegalArgumentException e) {
                            System.err.println("Niveau de consommation invalide. Doit etre BASSE, NORMAL, ou FORTE.");
                        }
                        break;
                    case 3:
                        // (Modifié pour utiliser rxe.find... et rxe.ajoutConnexion)
                        if(rxe.getGens().isEmpty() || rxe.getMaisons().isEmpty()) {
                            System.err.println("Vous ne pouvez pas ajouter une connexion : \n"
                                    + "veuillez d'abord ajouter un generateur ET une maison");
                            break;
                        }

                        System.out.println("• Ajout d'une connexion");
                        System.out.print("  Entrez le nom de la maison et du generateur (ex: M1 G1 ou G1 M1) : ");
                        String lineC = clavier.nextLine().trim();
                        String[] partsC = lineC.split(" ");

                        if (partsC.length != 2) {
                            System.err.println("Format invalide. Deux arguments attendus.");
                            break;
                        }

                        String arg1 = partsC[0];
                        String arg2 = partsC[1];

                        // On utilise les méthodes de rxe pour trouver les objets
                        Maison maison = rxe.findMaisonByName(arg1);
                        Generateur gen = rxe.findGenByName(arg2);

                        if (maison == null || gen == null) {
                            maison = rxe.findMaisonByName(arg2);
                            gen = rxe.findGenByName(arg1);
                        }

                        if (maison != null && gen != null) {
                            rxe.ajoutConnexion(maison, gen); // La logique est dans rxe
                        } else {
                            System.err.println("Maison ou Generateur introuvable.");
                        }
                        break;

                    // --- NOUVEAU CASE 4 ---
                    case 4:
                        System.out.println("• Supprimer une connexion");
                        if (rxe.getConnexions().isEmpty()) {
                            System.err.println("Aucune connexion a supprimer.");
                            break;
                        }

                        System.out.print("  Entrez le nom de la maison et du generateur (ex: M1 G1 ou G1 M1) : ");
                        String lineS = clavier.nextLine().trim();
                        String[] partsS = lineS.split(" ");

                        if (partsS.length != 2) {
                            System.err.println("Format invalide. Deux arguments attendus.");
                            break;
                        }

                        String argS1 = partsS[0];
                        String argS2 = partsS[1];

                        Maison mSup = rxe.findMaisonByName(argS1);
                        Generateur gSup = rxe.findGenByName(argS2);

                        if (mSup == null || gSup == null) {
                            mSup = rxe.findMaisonByName(argS2);
                            gSup = rxe.findGenByName(argS1);
                        }

                        if (mSup != null && gSup != null) {
                            rxe.supprimerConnexion(mSup, gSup); // La logique est dans rxe
                        } else {
                            System.err.println("Maison ou Generateur introuvable.");
                        }
                        break;

                    // --- CORRECTION CASE 5 (EX-4) ---
                    case 5:
                        System.out.println("• Fin de la creation et validation du reseau...");

                        // Appel de la validation
                        if (rxe.validerReseau()) {
                            System.out.println("  -> Reseau conforme. Passage au menu de calcul.");
                            choix = -1; // C'est valide, on sort de la boucle
                        } else {
                            System.err.println("  -> Reseau non conforme. Veuillez corriger les problemes listes.");
                            // On ne change pas 'choix', on reste dans le menu 1
                        }
                        break;
                    default:
                        System.err.println("Choix invalide. Veuillez reessayer.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Erreur de saisie. Veuillez entrer un nombre pour le menu.");
                clavier.nextLine(); // Nettoie le scanner
                choix = 0; // Réinitialise le choix pour rester dans la boucle
            }
        } while(choix != -1);


        // Si on est sorti de la boucle 1 (validation OK), on passe à la partie 2

        System.out.println("\n--- Passage au Menu Calcul ---");
        choix = 0;

        CoutRxElct crxe = new CoutRxElct(rxe);

        // partie 2
        do {
            try {
                menuCalcul();
                System.out.print("Votre choix : ");
                choix = clavier.nextInt();
                clavier.nextLine(); // Consomme le retour à la ligne

                switch(choix) {
                    case 1:
                        System.out.println("• Calcul du cout du reseau actuel ...");
                        double cout = crxe.calculeCoutRxE();
                        // Affichage des détails comme demandé
                        System.out.printf("  -> Disp(S) = %.2f\n", crxe.getDisp());
                        System.out.printf("  -> Surcharge(S) = %.2f\n", crxe.getSurcharge());
                        System.out.printf("  -> Le cout total du reseau est : %.2f\n", cout);
                        break;
                    case 2:
                        System.out.println("• Modifier une connexion");
                        crxe.modifierConnexion(clavier);
                        break;
                    case 3:
                        System.out.println("• Affichage du reseau actuel");
                        System.out.println(rxe.afficherConnexion());
                        break;
                    case 4:
                        System.out.println("FIN");
                        choix = -1; // Pour sortir de la boucle
                        break;
                    default:
                        System.err.println("Choix invalide. Veuillez reessayer.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Erreur de saisie. Veuillez entrer un nombre.");
                clavier.nextLine(); // Nettoie le scanner
                choix = 0; // Réinitialise le choix pour rester dans la boucle
            }
        } while(choix != -1);

        clavier.close();
    }
}