package tfg.jsemp.moneysaver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transaction implements Serializable {

    private String transactionId;
    private int quantity;
    private boolean isInCome;
    private LocalDate date;


    public Transaction(){
        this.quantity = 0;
        this.date = LocalDate.now();
    }


    public Transaction(int quantity, boolean isInCome) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = LocalDate.now();
    }

    public Transaction(int quantity, boolean isInCome, LocalDate date) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = date;
    }


    public String getTransactionId() {
        return transactionId;
    }


    public int getQuantity() {
        return quantity;
    }


    public boolean isInCome() {
        return isInCome;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setQuantity(int quantity) {
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
