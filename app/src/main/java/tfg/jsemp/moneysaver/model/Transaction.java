package tfg.jsemp.moneysaver.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transaction implements Serializable {

    private float quantity;
    private boolean isInCome;
    private Timestamp date;
    private String userId;
    private String accountId;
    private String categoryId;


    public Transaction(){
        this.quantity = 0;
        this.date = Timestamp.now();
    }


    public Transaction(float quantity, boolean isInCome, String userId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.userId = userId;
        this.date = Timestamp.now();
    }

    public Transaction(float quantity, boolean isInCome, String userId, String accountId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.userId = userId;
        this.date = Timestamp.now();
        this.accountId = accountId;
    }


    public Transaction(float quantity, boolean isInCome, Timestamp date, String userId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = date;
        this.userId = userId;
    }

    public Transaction(float quantity, boolean isInCome, Timestamp date, String userId, String accountId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.date = date;
        this.userId = userId;
        this.accountId = accountId;
    }


    public Transaction(float quantity, boolean isInCome, String userId, String categoryId, String accountId) {
        this.quantity = quantity;
        this.isInCome = isInCome;
        this.userId = userId;
        this.date = Timestamp.now();
        this.categoryId = categoryId;
        this.accountId = accountId;
    }


    public float getQuantity() {
        return quantity;
    }


    public boolean isInCome() {
        return isInCome;
    }


    public Timestamp getDate() {
        return date;
    }


    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }


    public void setInCome(boolean inCome) {
        isInCome = inCome;
    }


    public void setDate(Timestamp date) {
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


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "quantity=" + quantity +
                ", isInCome=" + isInCome +
                ", date=" + date +
                ", userId='" + userId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
