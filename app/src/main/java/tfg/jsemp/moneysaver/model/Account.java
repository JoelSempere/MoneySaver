package tfg.jsemp.moneysaver.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {

    private String name;
    private String categoryId;
    private String userId;
    private int total;


    public Account(){
        this.total = 0;
    }


    public Account(String userId, String name){
        this.userId = userId;
        this.name = name;
        this.total = 0;
    }


    public Account(String userId, String name, int total){
        this.userId = userId;
        this.name = name;
        this.total = total;
    }

    public Account(String categoryId, String userId, String name, int total){
        this.categoryId = categoryId;
        this.userId = userId;
        this.name = name;
        this.total = total;
    }


    public String getUserId() {
        return userId;
    }


    public int getTotal() {
        return total;
    }


    public void setTotal(int total) {
        this.total = total;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
