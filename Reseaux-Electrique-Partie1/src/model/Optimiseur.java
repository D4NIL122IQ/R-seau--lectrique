package model;

import java.util.ArrayList;
import java.util.Random;

public class Optimiseur {

    /**
     * Algorithme de Recuit Simulé (Simulated Annealing).
     * Permet de sortir des minimums locaux en acceptant parfois des solutions moins bonnes.
     * * @param rxe Le réseau électrique
     */
    public static void resolutionAutomatique(ReseauElectrique rxe) {
        if (rxe.getConnexions().isEmpty() || rxe.getGens().isEmpty()) return;

        CoutRxElct calculateur = new CoutRxElct(rxe);
        Random rand = new Random();

        // --- PARAMÈTRES DU RECUIT ---
        double temperature = 1000.0;   // Température initiale (agitation)
        double refroidissement = 0.9995; // Vitesse de refroidissement (plus c'est proche de 1, plus c'est lent et précis)
        double temperatureMin = 0.01;    // Température d'arrêt

        // État actuel
        double coutActuel = calculateur.calculeCoutRxE();
        System.out.println("  -> [Recuit] Coût initial : " + String.format("%.2f", coutActuel));

        // Pour garder la meilleure solution jamais rencontrée (Best Ever)
        // Car le recuit peut s'éloigner de la meilleure solution temporairement
        // Note: Idéalement il faudrait cloner le réseau, mais ici on va juste stocker le coût pour l'affichage
        double meilleurCoutConnu = coutActuel;

        int iteration = 0;

        while (temperature > temperatureMin) {

            // 1. CHOISIR UN VOISIN ALÉATOIRE (Comme l'algo naïf)
            // Le recuit simulé n'a pas besoin d'être "intelligent" dans le choix, 
            // c'est la gestion de la température qui fait la magie.
            ArrayList<Connexion> connexions = rxe.getConnexions();
            Connexion c = connexions.get(rand.nextInt(connexions.size()));

            Generateur ancienGen = c.getGen();
            ArrayList<Generateur> gens = rxe.getGens();
            Generateur nouveauGen = gens.get(rand.nextInt(gens.size()));

            if (ancienGen.equals(nouveauGen)) continue;

            // 2. CALCULER LE DELTA (Différence de coût)
            // On simule le mouvement
            int conso = c.getMs().getConso().getConso();

            // Test du coût SANS modifier définitivement (calcul manuel rapide pour performance)
            // Astuce: recalculer tout le coût via 'calculateur.calculeCoutRxE()' est lent.
            // Mais pour rester simple avec votre architecture, on fait le mouvement, on teste, on annule.

            effectuerDeplacement(c, ancienGen, nouveauGen, conso);
            double nouveauCout = calculateur.calculeCoutRxE();

            double delta = nouveauCout - coutActuel;

            // 3. DÉCISION D'ACCEPTATION (Coeur du Recuit Simulé)
            // Si delta < 0 : La nouvelle solution est MIEUX -> On garde toujours.
            // Si delta > 0 : La nouvelle solution est PIRE -> On garde PEUT-ÊTRE (selon température).

            boolean accepter = false;

            if (delta < 0) {
                accepter = true;
            } else {
                // Formule de Boltzmann : probabilité d'accepter une dégradation
                // Plus T est haut, plus on accepte. Plus T baisse, moins on accepte.
                double probabilite = Math.exp(-delta / (temperature * 0.1)); // *0.1 est un facteur d'échelle ajustable
                if (rand.nextDouble() < probabilite) {
                    accepter = true;
                }
            }

            if (accepter) {
                coutActuel = nouveauCout;
                if (coutActuel < meilleurCoutConnu) {
                    meilleurCoutConnu = coutActuel;
                }
            } else {
                // ROLLBACK : On annule le mouvement si on refuse
                effectuerDeplacement(c, nouveauGen, ancienGen, conso);
            }

            // 4. REFROIDISSEMENT
            temperature *= refroidissement;
            iteration++;
        }

        System.out.println("  -> Optimisation terminée (" + iteration + " itérations).");
        System.out.println("  -> [Recuit] Coût final : " + String.format("%.2f", coutActuel));
        if (coutActuel > meilleurCoutConnu) {
            System.out.println("  (Note : Une meilleure solution à " + String.format("%.2f", meilleurCoutConnu) + " a été croisée mais perdue par le refroidissement)");
        }
    }

    private static void effectuerDeplacement(Connexion c, Generateur source, Generateur dest, int conso) {
        source.soustraireCharge(conso);
        dest.setChargeActu(conso);
        c.setGen(dest);
    }
}