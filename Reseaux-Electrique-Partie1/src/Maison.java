
public class Maison {
	private String nomM;
	private Consomation conso;
	
	public Maison(String n, Consomation c) {
		nomM = n;
		conso = c;
	}
	
	public void setConso(Consomation c ) {
		conso = c;
	}
	
	public String toString() {
		return "Maison : " + nomM + " Consomation : " + conso.getConso() + " \n";
	}
}
