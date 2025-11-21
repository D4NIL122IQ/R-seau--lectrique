package fileOperation;

import java.util.ArrayList;
import java.lang.StringBuffer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.ReseauElectrique;
import model.Maison;
import model.Generateur;
import model.Maison;
import model.Connexion;

public class WriterFile {
	
	public static void saveReseau(ReseauElectrique rxe, String filename) {
		try (PrintWriter printW = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
			
			printW.println(saveGen(rxe.getGens()));
			printW.println(saveMai(rxe.getMaisons()));
			printW.println(saveCo(rxe.getConnexions()));
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	private static String saveGen(ArrayList<Generateur> gen) {
		StringBuffer temp = new StringBuffer("");
		for(Generateur g:gen) {
			temp.append("generateur(" + g.getNomG() + "," + g.getCapaciteMax() + ").\n");
		}
		
		return temp.toString();
	}
	
	private static String saveMai(ArrayList<Maison> mai) {
		StringBuffer temp = new StringBuffer("");
		for(Maison m:mai) {
			temp.append("generateur(" + m.getNomM() + "," + m.getConso() + ").\n");
		}
		
		return temp.toString();
	}
	
	private static String saveCo(ArrayList<Connexion> co) {
		StringBuffer temp = new StringBuffer("");
		for(Connexion c:co) {
			temp.append("connexion(" + c.getGen().getNomG() + "," + c.getMs().getNomM() + ").\n");
		}
		
		return temp.toString();
	}
}	
