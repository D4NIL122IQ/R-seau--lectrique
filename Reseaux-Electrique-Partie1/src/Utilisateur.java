import java.util.Scanner;

public class Utilisateur {
	private void menuCreation() {
		System.out.println("1 - Ajouter un generateur.");
		System.out.println("2 - Ajouter une maison.");
		System.out.println("3 - Ajouter une connexion.");
		System.out.println("4 - fin");
	}
	
	private void menuCalcul() {
		System.out.println("1 - Calculer le coût du réseau électrique actuel ");
		System.out.println("2 - Modifier une connexion.");
		System.out.println("3 - Afficher le reseaux");
		System.out.println("4 - fin");
	}
	
	public static void main(String []args) {
		int choix = 0;
		
		ReseauElectrique rxe = new ReseauElectrique(); // init rxe 
		
		Scanner clavier = new Scanner(System.in); 
		
		// partie 1
		do {
			switch(choix) {
			case 1:
				System.out.println("• Ajout d'un generateur");
				
				break;
			case 2:
				System.out.println("• Ajout d'une maison");
				break;
			case 3:
				if(rxe.equals(clavier)) {
					System.out.println("• Ajout d'une connexion");
				}else {
					System.err.println("Vous ne pouvez pas ajouter une connexion : \n"
							+ "veuillez d'abord ajouter un generateur ou une maison");
				}
				
				break;
			case 4:
				System.out.println("• FIN");
				choix = -1;
				break;
			default:
				break;
			}
		}while(choix != -1);
		
		
		choix = 0;
		
		CoutRxElct crxe = new CoutRxElct(rxe);
		
		// partie 2
		do {
			switch(choix) {
			case 1:
				System.out.println("• Calcul du cout du reseau actuel ...");
				break;
			case 2:
				System.out.println("• Modifier une connexion");
				break;
			case 3:
				System.out.println("• Affichage du reseau actuel");
				break;
			case 4:
				System.out.println("FIN");
				break;
			default:
				break;
			}
		}while(choix != -1);
		
	}
}
