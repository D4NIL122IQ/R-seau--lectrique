/*
 * Fichier : Utilisateur.java
 * (Classe principale mise à jour pour correspondre
 * aux exigences de saisie sur une seule ligne)
 */
import java.util.InputMismatchException;
import java.util.Scanner;

// J'ai retiré 'public' pour correspondre à votre code original
class Utilisateur {

    private static void menuCreation() {
        System.out.println("\n--- MENU CREATION ---");
        System.out.println("1 - Ajouter un generateur.");
        System.out.println("2 - Ajouter une maison.");
        System.out.println("3 - Ajouter une connexion.");
        System.out.println("4 - Fin creation");
    }

    private static void menuCalcul() {
        System.out.println("\n--- MENU CALCUL ---");
        System.out.println("1 - Calculer le coût du réseau électrique actuel ");
        System.out.println("2 - Modifier une connexion.");
        System.out.println("3 - Afficher le reseau");
        System.out.println("4 - Fin");
    }

    // --- DEBUT DES MODIFICATIONS ---
    // J'ai ajouté des méthodes d'aide pour trouver les objets par nom
    // afin de simplifier la logique de la connexion

    /**
     * Recherche une maison dans le réseau par son nom.
     * @param rxe Le réseau électrique
     * @param nom Le nom de la maison à chercher
     * @return L'objet Maison si trouvé, sinon null
     */
    private static Maison findMaisonByName(ReseauElectrique rxe, String nom) {
        for (Maison m : rxe.getMaisons()) {
            if (m.getNomM().equals(nom)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Recherche un générateur dans le réseau par son nom.
     * @param rxe Le réseau électrique
     * @param nom Le nom du générateur à chercher
     * @return L'objet Generateur si trouvé, sinon null
     */
    private static Generateur findGenByName(ReseauElectrique rxe, String nom) {
        for (Generateur g : rxe.getGens()) {
            if (g.getNomG().equals(nom)) {
                return g;
            }
        }
        return null;
    }
    // --- FIN DES AJOUTS ---


    public static void main(String []args) {
        int choix = 0;

        ReseauElectrique rxe = new ReseauElectrique(); // init rxe

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
                        // --- MODIFICATION CASE 1 ---
                        System.out.println("• Ajout d'un generateur");
                        System.out.print("  Entrez le nom et la capacite max (ex: G1 60) : ");
                        String lineG = clavier.nextLine().trim(); // .trim() enlève les espaces superflus
                        String[] partsG = lineG.split(" ");

                        if (partsG.length != 2) {
                            System.err.println("Format invalide. Deux arguments attendus.");
                            break;
                        }

                        try {
                            String nomG = partsG[0];
                            int capa = Integer.parseInt(partsG[1]);

                            rxe.ajoutGenerateur(new Generateur(nomG, capa));
                            // Message de confirmation (votre classe ReseauElectrique le fait déjà)

                        } catch (NumberFormatException e) {
                            System.err.println("Capacite invalide. Ce doit etre un nombre.");
                        }
                        break;
                    case 2:
                        // --- MODIFICATION CASE 2 ---
                        System.out.println("• Ajout d'une maison");
                        System.out.print("  Entrez le nom et la consommation (ex: M1 NORMAL) : ");
                        String lineM = clavier.nextLine().trim();
                        String[] partsM = lineM.split(" ");

                        if (partsM.length != 2) {
                            System.err.println("Format invalide. Deux arguments attendus.");
                            break;
                        }

                        String nomM = partsM[0];
                        String consoStr = partsM[1].toUpperCase(); // Met en majuscule pour correspondre à l'enum

                        Consomation consoEnum = null;
                        try {
                            // Convertit directement le String en Enum
                            consoEnum = Consomation.valueOf(consoStr);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Niveau de consommation invalide. Doit etre BASSE, NORMAL, ou FORTE.");
                            break;
                        }

                        rxe.ajoutMaison(new Maison(nomM, consoEnum));
                        // Message de confirmation (votre classe ReseauElectrique le fait déjà)

                        break;
                    case 3:
                        // --- MODIFICATION CASE 3 ---
                        if(rxe.getGens().isEmpty() || rxe.getMaisons().isEmpty()) {
                            System.err.println("Vous ne pouvez pas ajouter une connexion : \n"
                                    + "veuillez d'abord ajouter un generateur ET une maison");
                            break; // Sort du case 3
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

                        // On utilise les méthodes d'aide pour trouver les objets
                        // Premier essai : arg1=maison, arg2=generateur
                        Maison maison = findMaisonByName(rxe, arg1);
                        Generateur gen = findGenByName(rxe, arg2);

                        // Deuxième essai (inversé) : arg1=generateur, arg2=maison
                        if (maison == null || gen == null) {
                            maison = findMaisonByName(rxe, arg2);
                            gen = findGenByName(rxe, arg1);
                        }

                        // Vérification finale
                        if (maison != null && gen != null) {
                            rxe.ajoutConnexion(maison, gen);
                        } else {
                            System.err.println("Maison ou Generateur introuvable.");
                        }
                        break;
                    // --- FIN DES MODIFICATIONS ---
                    case 4:
                        System.out.println("• FIN de la creation");
                        choix = -1; // Pour sortir de la boucle
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


        // Si aucun générateur ou maison n'a été créé, la partie 2 n'a pas de sens.
        if (rxe.getGens().isEmpty() && rxe.getMaisons().isEmpty()) {
            System.out.println("Le reseau est vide. Fin du programme.");
            clavier.close();
            return; // Quitte le programme
        }

        choix = 0;

        CoutRxElct crxe = new CoutRxElct(rxe);

        // partie 2 (INCHANGÉE)
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
                        System.out.printf("  -> Le cout actuel du reseau est : %.2f\n", cout);
                        break;
                    case 2:
                        System.out.println("• Modifier une connexion");
                        // La méthode est vide dans votre classe CoutRxElct
                        crxe.modifierConnexion();
                        System.out.println("  -> NOTE: La fonction 'modifierConnexion' n'est pas encore implementee.");
                        break;
                    case 3:
                        System.out.println("• Affichage du reseau actuel");
                        // La méthode afficherReseau() de CoutRxElct est vide.
                        // On utilise donc directement la méthode de ReseauElectrique.
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

        clavier.close(); // Bonne pratique : fermer le scanner
    }
}