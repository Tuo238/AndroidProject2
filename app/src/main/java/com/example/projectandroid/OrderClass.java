package com.example.projectandroid;

public class OrderClass {
    private String lstFood;
    private  String total;

    public OrderClass(String lstFood, String total) {
        this.lstFood = lstFood;
        this.total = total;
    }

    public String getLstFood() {
        return lstFood;
    }

    public void setLstFood(String lstFood) {
        this.lstFood = lstFood;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
