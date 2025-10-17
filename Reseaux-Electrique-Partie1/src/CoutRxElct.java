
public class CoutRxElct {
	private Connexion c;
	
	public CoutRxElct(Connexion c) {
		this.c = c;
	}
	
	private double disp() {
		return 0;
	}
	
	private double surcharge() {
		return 0;
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
