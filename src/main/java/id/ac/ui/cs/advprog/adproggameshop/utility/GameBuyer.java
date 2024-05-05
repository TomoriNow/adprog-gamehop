package id.ac.ui.cs.advprog.adproggameshop.utility;

import id.ac.ui.cs.advprog.adproggameshop.exception.GameDoesNotExistException;
import id.ac.ui.cs.advprog.adproggameshop.exception.InsufficientFundsException;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import lombok.Getter;

import java.util.Date;

@Getter
public abstract class GameBuyer {
    Game game;
    User buyer;
    User seller;
    int amount;
    double baseCost;

    public Game buyGame(Game game, User buyer, int amount) {
        this.game = game;
        this.buyer = buyer;
        this.amount = amount;
        this.seller = getSeller();
        RuntimeException error = checkRestrictions(amount);
        if (error == null){
            this.baseCost = calculateCost(amount);
            updateGameAttributes(amount);
            updateSellerBalance(baseCost);
            updateBuyerBalance(baseCost);
        } else {
            throw error;
        }
        return this.game;
    }

    public User getSeller() {
        return this.game.getOwner();
    }
    public RuntimeException checkRestrictions(int amount) {
        if (game == null) {
            return  new GameDoesNotExistException();
        }
        if (buyer.getBalance() < game.getPrice() * amount) {
            return   new InsufficientFundsException();
        } else if (game.getQuantity() < 1) {
            return new GameDoesNotExistException();
        }
        return  null;
    }

    public abstract double calculateCost(int amount);

    public void updateGameAttributes(int amount) {
        game.setQuantity(game.getQuantity() - amount);
    }
    public abstract void updateSellerBalance(double cost);
    public abstract void updateBuyerBalance(double cost);
    public Transaction createTransactionRecord() {
        return new Transaction(this.buyer, this.seller, this.game.getName(), new Date(), this.amount, this.baseCost);
    }
}
