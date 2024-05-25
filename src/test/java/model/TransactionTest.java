package model;

import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.adproggameshop.model.Review;
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


    @Test
    public void testTransactionID() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);
        assertEquals(1L, transaction.getTransactionId());
    }

    @Test
    public void testBuyerID() {
        Transaction transaction = new Transaction();
        User buyer = new User();
        transaction.setBuyer(buyer);
        assertEquals(buyer, transaction.getBuyer());
    }

    @Test
    public void testSellerID() {
        Transaction transaction = new Transaction();
        User seller = new User();
        transaction.setSeller(seller);
        assertEquals(seller, transaction.getSeller());
    }

    @Test
    public void testProductName() {
        Transaction transaction = new Transaction();
        String productName = "Rainbow Six Siege";
        transaction.setProductName(productName);
        assertEquals(productName, transaction.getProductName());
    }

    @Test
    public void testDate() {
        Transaction transaction = new Transaction();
        Date date =  new Date();
        transaction.setDate(date);
        assertEquals(date, transaction.getDate());
    }

    @Test
    public void testAmount() {
        Transaction transaction = new Transaction();
        Integer amount =  69420;
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void testTotal() {
        Transaction transaction = new Transaction();
        Double total =  69.420;
        transaction.setTotal(total);
        assertEquals(total, transaction.getTotal());
    }






}