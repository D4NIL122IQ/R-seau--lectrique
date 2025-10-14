
import java.util.ArrayList;

public class ReseauElectrique {
	ArrayList<Maison> maisons;
	ArrayList<Generateur> generateurs;
	
	public ReseauElectrique(ArrayList<Maison> m, ArrayList<Generateur> g) {
		maisons = m;
		generateurs = g; 
	}
	
	public ReseauElectrique() {
		this(new ArrayList<Maison>(), new ArrayList<Generateur>());
	}
	
	public void ajoutMaison(Maison m) {
		
	}
	
	public void ajoutGenerateur(Generateur g) {
		
	}
	
	public void ajoutConnexion() {
		
	}
	
}
