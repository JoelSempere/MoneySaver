package tfg.jsemp.moneysaver.model;

import java.math.BigDecimal;

public class Category {

    private String categoryId;
    private String accountId;
    private String name;
    private BigDecimal income;
    private BigDecimal expense;


    public Category(){
        this.income = new BigDecimal("0");
        this.expense = new BigDecimal("0");
    }

    public Category(String accountId, String name){
        this.accountId = accountId;
        this.name = name;
        this.income = new BigDecimal("0");
        this.expense = new BigDecimal("0");
    }


    public Category(String accountId, String name, BigDecimal income, BigDecimal expense){
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


    public BigDecimal getIncome() {
        return income;
    }


    public BigDecimal getExpense() {
        return expense;
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setIncome(BigDecimal income) {
        this.income = income;
    }


    public void setExpense(BigDecimal expense) {
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
