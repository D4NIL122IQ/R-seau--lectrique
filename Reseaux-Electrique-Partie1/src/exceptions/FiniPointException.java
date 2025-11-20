package exceptions;

public class FiniPointException extends Exception{
	public FiniPointException() {
		super("La ligne ne fini pas avec un point");
	}
}
