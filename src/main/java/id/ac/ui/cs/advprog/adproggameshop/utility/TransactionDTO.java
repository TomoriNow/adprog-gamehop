package id.ac.ui.cs.advprog.adproggameshop.utility;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class TransactionDTO {
    private Long transactionId;
    private Date date;
    private int amount;
    private double total;
    private Long buyerUserId;
    private String buyerUsername;
    private Long sellerUserId;
    private String sellerUsername;
    private Long productProductId;
    private String productName;
  
    TransactionDTO(Long transactionId, Date date, int amount, double total, Long buyerUserId,
                   String buyerUsername, Long sellerUserId, String sellerUsername,
                   String productName) {
        this.transactionId = transactionId;
        this.date = date;
        this.amount = amount;
        this.total = total;
        this.buyerUserId = buyerUserId;
        this.buyerUsername = buyerUsername;
        this.sellerUserId = sellerUserId;
        this.sellerUsername = sellerUsername;
        this.productName = productName;
    }
}
