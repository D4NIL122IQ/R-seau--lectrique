import java.util.ArrayList;

public class CoutRxElct {
	private ArrayList<Connexion> co;
	private ArrayList<Generateur> gen;
	
	
	public CoutRxElct(ReseauElectrique rxe) {
		this.co = rxe.getConnexions();
		this.gen = rxe.getGens();
	}

	private double moyenneGen() {
		int somme = 0;

		for (Generateur g : gen) {
			somme += g.getChargeActu();
		}

		return somme / gen.size();
	}

	private double disp() {
		/*
		 * return : disp = SOMME(|Ug - Um|) tel que Ug la charge actuelle du generateur
		 * et Um la moyenne des charges
		 */
		double somme = 0;
		double Um = moyenneGen();
		for (Generateur g : gen) {
			somme += Math.abs(g.getChargeActu() - Um);
		}

		return somme;
	}

	private double surcharge() {
		double somme = 0 ;
		
		for(Generateur g: gen) {
			somme += Math.max(0, ((g.getChargeActu() - g.getCapaciteMax()) / g.getCapaciteMax()));
		}
		return somme;
	}

	public double calculeCoutRxE() {
		double severitePenalisation = 10;

		return disp() + severitePenalisation * surcharge();
	}

	public void modifierConnexion() {

	}

	public void afficherReseau() {

	}

}
