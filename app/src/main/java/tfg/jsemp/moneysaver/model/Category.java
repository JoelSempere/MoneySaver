package tfg.jsemp.moneysaver.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Category implements Serializable {

    private String categoryId;
    private String accountId;
    private String name;
    private int income;
    private int expense;


    public Category(){
        this.income = 0;
        this.expense = 0;
    }

    public Category(String accountId, String name){
        this.accountId = accountId;
        this.name = name;
        this.income = 0;
        this.expense = 0;
    }


    public Category(String accountId, String name, int income, int expense){
        this.accountId = accountId;
        this.name = name;
        this.income = income;
        this.expense = expense;
    }


    public String getCategoryId() {
        return categoryId;
    }


    public String getAccountId() {
        return accountId;
    }


    public String getName() {
        return name;
    }


    public int getIncome() {
        return income;
    }


    public int getExpense() {
        return expense;
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setIncome(int income) {
        this.income = income;
    }


    public void setExpense(int expense) {
        this.expense = expense;
    }


    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", income=" + income +
                ", expense=" + expense +
                '}';
    }


}
