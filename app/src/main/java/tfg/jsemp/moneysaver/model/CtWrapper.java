package tfg.jsemp.moneysaver.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/*****CLASE WRAPPER PARA TRABAJAR LOS RECYCLER VIEWS ANIDADOS*****/
public class CtWrapper implements Serializable {
    private Category category;
    private List<Transaction> transactions;


    public CtWrapper(Category category, List<Transaction> transactions) {
        this.category = category;
        this.transactions = transactions;
    }


    public CtWrapper(Category category) {
        this.category = category;

    }

    public CtWrapper() {

    }


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }


    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public String toString() {
        return "CtWrapper{" +
                "category=" + category +
                ", transactions=" + transactions +
                '}';
    }
}
