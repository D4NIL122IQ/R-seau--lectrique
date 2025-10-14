
public class Generateur {
	private String nomG;
	private int capaciteMax;
	private int capaciteActu;  // pour l'instant cette variable sera mise a jour a chaque ajout d'une connexion
	
	public Generateur(String n, int cm) {
		nomG = n;
		capaciteMax = cm;
		capaciteActu = cm;
	}
	
	public void setCapaciteActu(int consoMaison) {
		/*
		 * a chaque fois qu'on ajoute une connexion entre une maison et un generateur sa capacite diminue
		 */
		capaciteActu -= consoMaison; 
	}
	
	public String toString() {
		return "Generateur : " + nomG +" Capacite max : " + capaciteMax + "\n";
	}
}
