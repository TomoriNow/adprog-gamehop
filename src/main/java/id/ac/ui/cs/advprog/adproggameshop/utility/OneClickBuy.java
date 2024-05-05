package id.ac.ui.cs.advprog.adproggameshop.utility;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;

public class OneClickBuy extends GameBuyer{
    public Game buyGame(Game game, User buyer) {
        return buyGame(game, buyer, 1);
    }


    @Override
    public double calculateCost(int amount) {
        return game.getPrice();
    }

    @Override
    public void updateSellerBalance(double baseCost) {
        seller.setBalance(seller.getBalance() + baseCost);
    }

    @Override
    public void updateBuyerBalance(double baseCost) {
        buyer.setBalance(buyer.getBalance() - baseCost);
    }
}
