package tfg.jsemp.moneysaver.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Category implements Serializable {

    private String categoryId;
    private String name;
    private float income;
    private float expense;
    private String image;


    public Category(){
        this.income = 0;
        this.expense = 0;
        this.image = "";
        this.name = "uknown";
    }

    public Category(String name){
        this.name = name;
        this.income = 0;
        this.expense = 0;
        this.image = "";
    }


    public Category(String name,String image){
        this.name = name;
        this.image = image;
        this.income = 0;
        this.expense = 0;
    }


    public Category(String name, float income, float expense){
        this.name = name;
        this.income = income;
        this.expense = expense;
        this.image = "";
    }


    public Category(String name, String image, float income, float expense){
        this.name = name;
        this.image = image;
        this.income = income;
        this.expense = expense;
        this.image = "";
    }


    public String getCategoryId() {
        return categoryId;
    }


    public String getName() {
        return name;
    }


    public float getIncome() {
        return income;
    }


    public float getExpense() {
        return expense;
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setIncome(float income) {
        this.income = income;
    }


    public void setExpense(float expense) {
        this.expense = expense;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                ", income=" + income +
                ", expense=" + expense +
                '}';
    }


}
