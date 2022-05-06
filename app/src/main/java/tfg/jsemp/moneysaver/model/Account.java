package tfg.jsemp.moneysaver.model;

import java.math.BigDecimal;

public class Account {

    private String accountId;
    private String userId;
    private BigDecimal total;


    public Account(){
        this.total = new BigDecimal("0");
    }


    public Account(String userId){
        this.userId = userId;
        this.total = new BigDecimal("0");
    }


    public Account(String userId, BigDecimal total){
        this.userId = userId;
        this.total = total;
    }


    public String getAccountId() {
        return accountId;
    }


    public String getUserId() {
        return userId;
    }


    public BigDecimal getTotal() {
        return total;
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", userId='" + userId + '\'' +
                ", total=" + total +
                '}';
    }


}
