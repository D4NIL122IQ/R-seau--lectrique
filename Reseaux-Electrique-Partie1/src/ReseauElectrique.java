
import java.util.ArrayList;
import java.lang.StringBuffer;

public class ReseauElectrique {
	ArrayList<Maison> maisons;
	ArrayList<Generateur> generateurs;
	ArrayList<Connexion> connexion;

	public ReseauElectrique(ArrayList<Maison> m, ArrayList<Generateur> g) {
		maisons = m;
		generateurs = g;
		connexion = new ArrayList<>();
	}

	public ReseauElectrique() {
		this(new ArrayList<Maison>(), new ArrayList<Generateur>());
	}

	public void ajoutMaison(Maison m) {
		if (maisons.isEmpty()) {
			maisons.add(m);
		} else {
			for (Maison t : maisons) {
				if (t.equals(m)) {
					t.setConso(m.getConso());
					System.out.println("Maison existante maj de ses info" + t);
					return;
				}
			}
			maisons.add(m);
		}
	}

	public void ajoutGenerateur(Generateur g) {
		if (generateurs.isEmpty()) {
			generateurs.add(g);
		} else {
			for (Generateur t : generateurs) {
				if (t.equals(g)) {
					t.setCapaMax(g.getCapaciteMax());
					System.out.println("Generateur existant maj de ses info" + t);
					return;
				}
			}
			generateurs.add(g);
		}
	}

	public void ajoutConnexion(Maison m, Generateur g) {
		Connexion temp = new Connexion(m, g);
		if (connexion.isEmpty()) {
			connexion.add(temp);
			g.setChargeActu(m.getConso().getConso());
		} else if (connexion.contains(temp)) {
			System.out.println("connexioon existante");
		} else if(Connexion.contientMaison(m.getNomM(), connexion)){
			System.out.println("Maison deja connecté");
		}else {
			connexion.add(temp);
			g.setChargeActu(m.getConso().getConso());
		}
	}

	public String afficherMaisons() {
		StringBuffer temp = new StringBuffer();

		for (Maison m : maisons) {
			temp.append(m.toString());
		}

		return temp.toString();
	}

	public String affichergenerateur() {
		StringBuffer temp = new StringBuffer();

		for (Generateur g : generateurs) {
			temp.append(g.toString());
		}

		return temp.toString();
	}

	public String afficherConnexion() {
		StringBuffer t = new StringBuffer("Reseau de distribution d'electricite : \n");

		for (Generateur g : generateurs) {
			t.append("+----------------+\n");
			t.append("| " + g.getNomG() +"		|\n");
			t.append("+----------------+\n");
			t.append("        |\n");
			t.append("        | alimente ↓");
			for (Connexion c : connexion) {
				if(c.getGen().equals(g)) {
					t.append("+--> " + c.getMs().getNomM() );
				}
			}
			t.append("\n");
		}
		return t.toString();
	}
}
