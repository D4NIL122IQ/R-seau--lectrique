package exceptions;

public class MGException extends Exception{
	public MGException() {
		super("Nom de la maison ou/et generateur faux");
	}
}
