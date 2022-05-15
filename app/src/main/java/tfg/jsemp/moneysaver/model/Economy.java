package tfg.jsemp.moneysaver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Economy implements Serializable {

    private String userId;
    private int total;


    public Economy(){
        this.total = 0;
    }


    public Economy(String userId){
        this.userId = userId;
        this.total = 0;
    }


    public Economy(String userId, int total){
        this.userId = userId;
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


    @Override
    public String toString() {
        return "Economy{" +
                "userId='" + userId + '\'' +
                ", total=" + total +
                '}';
    }


}
