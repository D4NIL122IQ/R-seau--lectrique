import java.util.ArrayList;

public class Connexion {
	private Maison m;
	private Generateur g;

	public Connexion(Maison m, Generateur g) {
		this.m = m;
		this.g = g;
	}

	public Generateur getGen() {
		return g;
	}

	public Maison getMs() {
		return m;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Connexion) {
			Connexion temp = (Connexion) o;
			if ((temp.getGen().equals(g)) && (temp.getMs().equals(m))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean contientMaison(String nomM, ArrayList<Connexion> connexion) {
		for (Connexion c : connexion) {
			if (c.getMs().getNomM().equals(nomM)) {
				return true;
			}
		}
		return false;
	}
}
