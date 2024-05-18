package id.ac.ui.cs.advprog.adproggameshop.exception;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;

public class NoGameLeftException extends RuntimeException {

    public NoGameLeftException() {
        super("We do not have any more copies of that game");
    }

    public NoGameLeftException(Game game) {
        super("We do not have any more copies of the game: "+game.getName());
    }

    public NoGameLeftException(String message) {
        super(message);
    }

    public NoGameLeftException(String message, Throwable cause) {
        super(message, cause);
    }
}
