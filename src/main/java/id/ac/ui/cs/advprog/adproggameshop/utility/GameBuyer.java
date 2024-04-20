package id.ac.ui.cs.advprog.adproggameshop.utility;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.GameRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.TransactionRepository;
import id.ac.ui.cs.advprog.adproggameshop.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        if (checkRestrictions()){
            this.baseCost = calculateCost(amount);
            updateGameAttributes(amount);
            updateSellerBalance(baseCost);
            updateBuyerBalance(baseCost);
        } else {
            throw new InsufficientFundsException();
        }
        return this.game;
    }

    public User getSeller() {
        return this.game.getOwner();
    }
    public abstract boolean checkRestrictions();

    public abstract double calculateCost(int amount);

    public void updateGameAttributes(int amount) {
        game.setQuantity(game.getQuantity() - amount);
    }
    public abstract void updateSellerBalance(double cost);
    public abstract void updateBuyerBalance(double cost);
    public Transaction createTransactionRecord() {
        return new Transaction(this.buyer, this.seller, this.game, new Date(), this.amount, this.baseCost);
    }
}
