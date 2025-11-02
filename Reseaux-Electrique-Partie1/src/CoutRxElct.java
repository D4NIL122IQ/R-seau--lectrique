// Fichier : CoutRxElct.java
// (Version corrigée qui utilise une référence au lieu de copies)

import java.util.ArrayList;

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

    public void modifierConnexion() {
        // La logique sera à implémenter ici
    }

    public void afficherReseau() {
        // Cette méthode peut maintenant appeler directement celle du réseau
        System.out.println(rxe.afficherConnexion());
    }

}