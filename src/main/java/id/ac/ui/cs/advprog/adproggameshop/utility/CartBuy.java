package id.ac.ui.cs.advprog.adproggameshop.utility;

import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;

public class CartBuy extends GameBuyer{
    @Override
    public double calculateCost(int amount) {
        return game.getPrice() * amount;
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
