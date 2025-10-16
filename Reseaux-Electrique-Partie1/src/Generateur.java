
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
	
	public void setCapaMax(int capa) {
		capaciteMax = capa;
		capaciteActu = capa;
	}

	public String getNomG() {
		return nomG;
	}

	public int getCapaciteMax() {
		return capaciteMax;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Generateur) {
			Generateur t = (Generateur) o;
			if(t.getNomG().equals(nomG)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	
	@Override
	public String toString() {
		return "Generateur : " + nomG +" Capacite max : " + capaciteMax + "kwh\n";
	}

	
}
