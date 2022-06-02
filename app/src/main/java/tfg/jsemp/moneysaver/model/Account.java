package tfg.jsemp.moneysaver.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {

    private String name;
    private String accountId;
    private String userId;
    private float total;


    public Account(){
        this.total = 0;
    }


    public Account(String userId, String name){
        this.userId = userId;
        this.name = name;
        this.total = 0;
    }


    public Account(String userId, String name, float total){
        this.userId = userId;
        this.name = name;
        this.total = total;
    }

    public Account(String accountId, String userId, String name, float total){
        this.accountId = accountId;
        this.userId = userId;
        this.name = name;
        this.total = total;
    }


    public String getUserId() {
        return userId;
    }


    public float getTotal() {
        return total;
    }


    public void setTotal(float total) {
        this.total = total;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String categoryId) {
        this.accountId = accountId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", total=" + total +
                '}';
    }
}
