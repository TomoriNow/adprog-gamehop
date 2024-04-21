package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.repository.TransactionRepository;
import id.ac.ui.cs.advprog.adproggameshop.service.TransactionServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testFindAllByBuyerOrSeller() {
        User buyer = new User();
        User seller = new User();

        List<TransactionDTO> mockTransactionDTOs = new ArrayList<>();
        mockTransactionDTOs.add(new TransactionDTO(1L, new Date(), 3, 150.00, 2L, "buyerName", 3L, "sellerName", 4L, "productX"));
        mockTransactionDTOs.add(new TransactionDTO(2L, new Date(), 5, 250.00, 5L, "buyerName2", 6L, "sellerName2", 7L, "productY"));

        when(transactionRepository.findAllByBuyerOrSeller(buyer, seller)).thenReturn(mockTransactionDTOs);

        List<TransactionDTO> foundTransactionDTOs = transactionService.findAllByBuyerOrSeller(buyer, seller);

        assertNotNull(foundTransactionDTOs);
        assertEquals(2, foundTransactionDTOs.size());
        assertEquals(1L, foundTransactionDTOs.getFirst().getTransactionId());
        assertEquals(3, foundTransactionDTOs.getFirst().getAmount());
        assertEquals(150.00, foundTransactionDTOs.getFirst().getTotal(), 0.01);
        assertEquals(2L, foundTransactionDTOs.getFirst().getBuyerUserId());
        assertEquals("buyerName", foundTransactionDTOs.getFirst().getBuyerUsername());
        assertEquals("productX", foundTransactionDTOs.getFirst().getProductName());
    }
}
