package utility;

import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
public class TransactionDTOTest {
    TransactionDTO transactionDTO;
    Date date;

    @BeforeEach
    public void setup() {
        date = new Date();
        transactionDTO = new TransactionDTO(1L, date, 5, 4000.5, 11L,
                "buyer", 111L, "seller", "game");
    }

    @Test
    public void testConstructor() {
        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetTransactionId() {
        transactionDTO.setTransactionId(2L);

        assertEquals(2L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date();
        transactionDTO.setDate(newDate);

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(newDate, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetAmount() {
        transactionDTO.setAmount(10);

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(10, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetTotal() {
        transactionDTO.setTotal(25.55);

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(25.55, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetBuyerUserId() {
        transactionDTO.setBuyerUserId(22L);

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(22L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetBuyerUsername() {
        transactionDTO.setBuyerUsername("buyer2");

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer2", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetSellerUserId() {
        transactionDTO.setSellerUserId(222L);

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(222L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetSellerUsername() {
        transactionDTO.setSellerUsername("seller2");

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller2", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetProductName() {
        transactionDTO.setProductName("game2");

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game2", transactionDTO.getProductName());
        assertNull(transactionDTO.getProductProductId());
    }

    @Test
    public void testSetProductProductId() {
        transactionDTO.setProductProductId(2222L);

        assertEquals(1L, transactionDTO.getTransactionId());
        assertEquals(date, transactionDTO.getDate());
        assertEquals(5, transactionDTO.getAmount());
        assertEquals(4000.5, transactionDTO.getTotal());
        assertEquals(11L, transactionDTO.getBuyerUserId());
        assertEquals("buyer", transactionDTO.getBuyerUsername());
        assertEquals(111L, transactionDTO.getSellerUserId());
        assertEquals("seller", transactionDTO.getSellerUsername());
        assertEquals("game", transactionDTO.getProductName());
        assertEquals(2222L, transactionDTO.getProductProductId());
    }
}
