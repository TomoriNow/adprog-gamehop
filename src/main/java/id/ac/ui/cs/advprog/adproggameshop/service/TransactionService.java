package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.model.User;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAllByBuyerOrSeller(User buyer, User seller);
}