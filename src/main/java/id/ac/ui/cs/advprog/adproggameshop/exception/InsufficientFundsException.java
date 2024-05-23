package id.ac.ui.cs.advprog.adproggameshop.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds to buy the product");
    }

    public InsufficientFundsException(double price, double balance) {
        super("Insufficient funds to buy the product. Total price = "+ price +", Your Balance = "+ balance);
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
