import java.util.Scanner;

public class TestRxElct {
	public static void main(String[]args) {
		Scanner clavier = new Scanner(System.in);
		ReseauElectrique rxe = new ReseauElectrique();
		
		Generateur g1 = new Generateur("G1", 60);
		Generateur g2 = new Generateur("G2", 40);

		rxe.ajoutGenerateur(g1);
		rxe.ajoutGenerateur(g2);
		
		rxe.ajoutGenerateur(new Generateur("G2", 50)); // maj generateur
		System.out.println(rxe.affichergenerateur());
		
		Maison m1 = new Maison("M1", Consomation.BASSE);
		Maison m2 = new Maison("M2", Consomation.FORTE);
		Maison m3 = new Maison("M3", Consomation.NORMAL);
		Maison m4 = new Maison("M4", Consomation.NORMAL);
		
		rxe.ajoutMaison(m1);
		rxe.ajoutMaison(m2);
		rxe.ajoutMaison(m3);
		rxe.ajoutMaison(m4);
		
		rxe.ajoutMaison(new Maison("M1" , Consomation.FORTE)); // maj d'une maison
		
		System.out.println(rxe.afficherMaisons());
		
		rxe.ajoutConnexion(m1, g1);
		rxe.ajoutConnexion(m1, g1);
		rxe.ajoutConnexion(m2, g2);
		rxe.ajoutConnexion(m2, g1);
		rxe.ajoutConnexion(m4, g2);
		
		
		System.out.println(rxe.afficherConnexion());
		
		
	}
}
