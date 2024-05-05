package id.ac.ui.cs.advprog.adproggameshop.exception;

public class NoGameLeftException extends RuntimeException {

    public NoGameLeftException() {
        super("We do not have any more copies of that game");
    }

    public NoGameLeftException(String message) {
        super(message);
    }

    public NoGameLeftException(String message, Throwable cause) {
        super(message, cause);
    }
}
