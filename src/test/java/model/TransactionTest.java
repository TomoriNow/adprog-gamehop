package model;

import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.adproggameshop.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.model.Game;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import java.util.Date;

public class TransactionTest {
    private Transaction transaction;
    private User buyer;
    private User seller;
    private Date initialDate;

    @BeforeEach
    public void setUp() {
        buyer = new User();
        seller = new User();
        initialDate = new Date();
        transaction = new Transaction();
    }

    @Test
    public void testSetBuyer() {
        transaction.setBuyer(buyer);
        assertEquals(buyer, transaction.getBuyer());
    }

    @Test
    public void testSetSeller() {
        transaction.setSeller(seller);
        assertEquals(seller, transaction.getSeller());
    }

    @Test
    public void testSetProductName() {
        String productName = "GameController";
        transaction.setProductName(productName);
        assertEquals(productName, transaction.getProductName());
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date();
        transaction.setDate(newDate);
        assertEquals(newDate, transaction.getDate());
    }

    @Test
    public void testSetAmount() {
        int amount = 5;
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void testSetTotal() {
        double total = 499.95;
        transaction.setTotal(total);
        assertEquals(total, transaction.getTotal());
    }
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