package tfg.jsemp.moneysaver.model;

import java.math.BigDecimal;

public class Economy {

    private String userId;
    private BigDecimal total;


    public Economy(){
        this.total = new BigDecimal("0");
    }


    public Economy(String userId){
        this.userId = userId;
        this.total = new BigDecimal("0");
    }


    public Economy(String userId, BigDecimal total){
        this.userId = userId;
        this.total = total;
    }


    public String getUserId() {
        return userId;
    }


    public BigDecimal getTotal() {
        return total;
    }


    public void setTotal(BigDecimal total) {
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
