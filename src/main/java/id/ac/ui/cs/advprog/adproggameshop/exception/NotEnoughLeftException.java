package id.ac.ui.cs.advprog.adproggameshop.exception;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;

public class NotEnoughLeftException extends RuntimeException {


    public NotEnoughLeftException(Game game) {
        super("We do not have enough copies of the game: "+game.getName());
    }
}
