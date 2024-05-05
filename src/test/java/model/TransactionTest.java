package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import java.util.Date;

public class TransactionTest {

    @Test
    public void testTransactionConstructor() {
        User buyer = new User();
        User seller = new User();
        String product_name = "Game";
        Date date = new Date();
        int amount = 5;
        double total = 150.50;

        Transaction transaction = new Transaction(buyer, seller, product_name, date, amount, total);
        assertNotNull(transaction);
        assertEquals(buyer, transaction.getBuyer());
        assertEquals(seller, transaction.getSeller());
        assertEquals(product_name, transaction.getProductName());
        assertEquals(date, transaction.getDate());
        assertEquals(amount, transaction.getAmount());
        assertEquals(total, transaction.getTotal());
    }
}