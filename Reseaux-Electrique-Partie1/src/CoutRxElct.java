// Fichier : CoutRxElct.java
// (Version corrigée qui utilise une référence au lieu de copies)

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoutRxElct {
    // --- MODIFICATION ---
    // On ne stocke plus les listes, mais le réseau lui-même
    private ReseauElectrique rxe;


    public CoutRxElct(ReseauElectrique rxe) {
        // On garde la référence vers l'objet rxe principal
        this.rxe = rxe;
    }

    private double moyenneGen() {
        // On utilise rxe.getGens() pour avoir la liste à jour
        ArrayList<Generateur> gen = rxe.getGens();

        if (gen.isEmpty()) {
            return 0;
        }

        int somme = 0;
        for (Generateur g : gen) {
            somme += g.getChargeActu();
        }

        return (double) somme / gen.size();
    }

    private double disp() {
        /*
         * return : disp = SOMME(|Ug - Um|) tel que Ug la charge actuelle du generateur
         * et Um la moyenne des charges
         */

        // On utilise rxe.getGens() pour avoir la liste à jour
        ArrayList<Generateur> gen = rxe.getGens();

        double somme = 0;
        double Um = moyenneGen();
        for (Generateur g : gen) {
            somme += Math.abs(g.getChargeActu() - Um);
        }

        return somme;
    }

    private double surcharge() {
        double somme = 0 ;

        // On utilise rxe.getGens() pour avoir la liste à jour
        ArrayList<Generateur> gen = rxe.getGens();

        for(Generateur g: gen) {
            // Éviter la division par zéro si la capacité est 0
            if (g.getCapaciteMax() > 0) {
                somme += Math.max(0, ((double) g.getChargeActu() - g.getCapaciteMax()) / g.getCapaciteMax());
            } else if (g.getChargeActu() > 0) {
                // Si capacité 0 mais charge > 0, c'est une surcharge
                somme += 1.0; // Pénalité fixe (ou autre logigue)
            }
        }
        return somme;
    }

    public double calculeCoutRxE() {
        // On utilise rxe.getGens() pour avoir la liste à jour
        if (rxe.getGens().isEmpty()) {
            return 0;
        }

        double severitePenalisation = 10;
        return disp() + severitePenalisation * surcharge();
    }

    public void modifierConnexion(Scanner clavier) {

        // 0. Vérifier s'il y a des connexions
        if (rxe.getConnexions().isEmpty()) {
            System.err.println("Aucune connexion à modifier.");
            return;
        }

        System.out.println("--- Modification d'une connexion ---");
        System.out.println(rxe.afficherConnexion()); // Montrer l'état actuel

        System.out.print("  Nom de la maison à déplacer : ");
        String nomMaison = clavier.nextLine();

        // 1. Trouver l'objet Maison ET sa connexion
        Maison maisonAModifier = null;
        Connexion connexionAModifier = null;

        for (Connexion c : rxe.getConnexions()) {
            if (c.getMs().getNomM().equals(nomMaison)) {
                maisonAModifier = c.getMs();
                connexionAModifier = c;
                break;
            }
        }

        if (maisonAModifier == null) {
            System.err.println("Maison '" + nomMaison + "' introuvable ou non connectée.");
            return;
        }

        System.out.print("  Nom du NOUVEAU générateur : ");
        String nomNouveauGen = clavier.nextLine();

        // 2. Trouver l'objet Generateur de destination
        Generateur nouveauGen = null;
        for (Generateur g : rxe.getGens()) {
            if (g.getNomG().equals(nomNouveauGen)) {
                nouveauGen = g;
                break;
            }
        }

        if (nouveauGen == null) {
            System.err.println("Générateur '" + nomNouveauGen + "' introuvable.");
            return;
        }

        // 3. Récupérer l'ancien générateur
        Generateur ancienGen = connexionAModifier.getGen();

        // 4. Vérifier si on ne se reconnecte pas au même
        if (ancienGen.equals(nouveauGen)) {
            System.out.println("  -> La maison est déjà connectée à ce générateur.");
            return;
        }

        // 5. Exécuter la modification
        int conso = maisonAModifier.getConso().getConso();

        System.out.println("  ... Déconnexion de " + ancienGen.getNomG() + " ...");
        ancienGen.soustraireCharge(conso);

        System.out.println("  ... Connexion à " + nouveauGen.getNomG() + " ...");
        nouveauGen.setChargeActu(conso);

        // 6. Mettre à jour la connexion elle-même
        connexionAModifier.setGen(nouveauGen);

        System.out.println("  -> Modification terminée.");
    }

    public void afficherReseau() {
        // Cette méthode peut maintenant appeler directement celle du réseau
        System.out.println(rxe.afficherConnexion());
    }

}