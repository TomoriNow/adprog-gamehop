package id.ac.ui.cs.advprog.adproggameshop.exception;

public class GameDoesNotExistException extends RuntimeException {

    public GameDoesNotExistException() {
        super("That game no longer exists");
    }

    public GameDoesNotExistException(String message) {
        super(message);
    }

    public GameDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}