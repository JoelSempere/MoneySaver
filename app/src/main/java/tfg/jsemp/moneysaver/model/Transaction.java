package tfg.jsemp.moneysaver.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transaction {

    private String transactionId;
    private BigDecimal quantity;
    private boolean isInCome;
    private LocalDate date;


    public Transaction(){
        this.quantity = new BigDecimal("0");
        this.date = LocalDate.now();
    }


    public Transaction(BigDecimal quantity, boolean isInCome) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = LocalDate.now();
    }

    public Transaction(BigDecimal quantity, boolean isInCome, LocalDate date) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = date;
    }


    public String getTransactionId() {
        return transactionId;
    }


    public BigDecimal getQuantity() {
        return quantity;
    }


    public boolean isInCome() {
        return isInCome;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }


    public void setInCome(boolean inCome) {
        isInCome = inCome;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", quantity=" + quantity +
                ", isInCome=" + isInCome +
                ", date=" + date +
                '}';
    }


}
