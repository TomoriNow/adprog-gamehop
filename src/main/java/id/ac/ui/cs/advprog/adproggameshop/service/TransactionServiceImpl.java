package id.ac.ui.cs.advprog.adproggameshop.service;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.TransactionRepository;
import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionDTO> findAllByBuyerOrSeller(User buyer, User seller) {
        return transactionRepository.findAllByBuyerOrSeller(buyer, seller);
    }
}
