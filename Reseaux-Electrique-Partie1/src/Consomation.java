
public enum Consomation {
	BASSE(10), NORMAL(20), FORTE(40);
	
	private final int conso;
	
	private Consomation(int conso) {
		this.conso = conso;
	}
	
	public int getConso() {
		return this.conso;
	}
}
