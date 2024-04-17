package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.Transaction;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> findAllByBuyerOrSeller(User buyer, User seller);
}