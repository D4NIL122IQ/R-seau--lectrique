package fileOperation;

import model.ReseauElectrique;
import model.Generateur;
import model.Maison;
import model.Connexion;
import model.Consomation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exceptions.ArgumentNonDefiniException;
import exceptions.ElementErroneException;
import exceptions.OrdreInstanceException;
import exceptions.FiniPointException;
import exceptions.NormbreParametreExeception;
import exceptions.MGException;

public class ParserFile {
	private static String[] element = { "generateur", "maison", "connexion" }; // tableau qio contient les elements
																				// possible et dans l'ordre
	/*
	 * lit un fichier qui contient un reseau electroique et retourne une instance de
	 * reseau electrique
	 * 
	 * @param filepath chamin absolu du fichier
	 * 
	 */

	public static ReseauElectrique parser(String filepath) throws ArgumentNonDefiniException, ElementErroneException,
			OrdreInstanceException, FiniPointException, MGException, NormbreParametreExeception {
		ReseauElectrique rxe = null;
		FileReader f;
		BufferedReader bReader;
		int i = 0;
		String ligne;

		try {
			f = new FileReader(filepath);
			bReader = new BufferedReader(f);

			while ((ligne = bReader.readLine()) != null) {
				if (ligne.endsWith(".")) { // voir si chaque ligne fini avec un point
					if (ligne.startsWith("generateur")) {
						if (i > 0) {
							throw new OrdreInstanceException("Ordre non respecter");
						}
						rxe.ajoutGenerateur(parserGen(ligne));

					} else if (ligne.startsWith("maison")) {
						if (i == 0) {
							i = 1;
							rxe.ajoutMaison(parserMaison(ligne));
						} else if (i > 1) {
							throw new OrdreInstanceException("Ordre non respecter");
						}
					} else if (ligne.startsWith("connexion")) {
						i = 3;

						rxe.ajoutConnexion(parserCo(ligne, rxe));

					} else {
						// ligne inconnue → tu peux gérer comme tu veux
						throw new ElementErroneException("Element faux");
					}
				} else {
					throw new FiniPointException();
				}

			}

		} catch (FileNotFoundException fne) { // erreur ouverture du fichier
			System.out.println("Fichier introuvable : " + filepath);
		} catch (IOException e) { // erreur pendant la lecture
			System.out.println("Erreur pendant la lecture du fichier");

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		return rxe;
	}

	private static Generateur parserGen(String ligne) {
		String[] data = ligne.split("\\(")[1].split("\\).")[0].split(",");

		return new Generateur(data[0], Integer.parseInt(data[1]));
	}

	private static Maison parserMaison(String ligne) throws ArgumentNonDefiniException {
		String[] data = ligne.split("\\(")[1].split("\\).")[0].split(",");
		Maison temp;
		switch (data[1]) {
		case "BASSE":
			temp = new Maison(data[0], Consomation.BASSE);
			break;
		case "NORMAL":
			temp = new Maison(data[0], Consomation.NORMAL);
			break;
		case "FORTE":
			temp = new Maison(data[0], Consomation.FORTE);
			break;
		default:
			throw new ArgumentNonDefiniException("");
		}

		return temp;
	}

	private static Connexion parserCo(String ligne, ReseauElectrique rxe)
			throws NormbreParametreExeception, MGException {
		String[] data = ligne.split("\\(")[1].split("\\).")[0].split(",");

		if (data.length != 2) {
			throw new NormbreParametreExeception("Parametre probleme");
		}
		Connexion temp;

		String arg1 = data[0];
		String arg2 = data[1];

		// trouver le bon ordre des arguments
		Maison maison = rxe.findMaisonByName(arg1);
		Generateur gen = rxe.findGenByName(arg2);

		if (maison == null || gen == null) {
			maison = rxe.findMaisonByName(arg2);
			gen = rxe.findGenByName(arg1);
		}

		if (maison != null && gen != null) {
			temp = new Connexion(maison, gen); // La logique est dans rxe
		} else {
			throw new MGException();
		}

		return temp;
	}

}
