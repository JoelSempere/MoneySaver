package tfg.jsemp.moneysaver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transaction implements Serializable {

    private int quantity;
    private boolean isInCome;
    private LocalDate date;
    private String userId;
    private String accountId;


    public Transaction(){
        this.quantity = 0;
        this.date = LocalDate.now();
    }


    public Transaction(int quantity, boolean isInCome, String userId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.userId = userId;
        this.date = LocalDate.now();
    }

    public Transaction(int quantity, boolean isInCome, String userId, String accountId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.userId = userId;
        this.date = LocalDate.now();
        this.accountId = accountId;
    }

    public Transaction(int quantity, boolean isInCome, LocalDate date, String userId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = date;
        this.userId = userId;
    }

    public Transaction(int quantity, boolean isInCome, LocalDate date, String userId, String accountId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = date;
        this.userId = userId;
        this.accountId = accountId;
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

    public String getUserId() {
        return userId;
    }


    public String getAccountId() {
        return accountId;
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "quantity=" + quantity +
                ", isInCome=" + isInCome +
                ", date=" + date +
                ", userId='" + userId + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }

}
