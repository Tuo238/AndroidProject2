package com.example.projectandroid;

public class cartData {
    String idFood;
    String pathFood;
    String lstnameTopping;
    String priceTopping;

    public cartData(String idFood, String pathFood, String lstnameTopping, String priceTopping) {
        this.idFood = idFood;
        this.pathFood = pathFood;
        this.lstnameTopping = lstnameTopping;
        this.priceTopping = priceTopping;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getPathFood() {
        return pathFood;
    }

    public void setPathFood(String pathFood) {
        this.pathFood = pathFood;
    }

    public String getLstnameTopping() {
        return lstnameTopping;
    }

    public void setLstnameTopping(String lstnameTopping) {
        this.lstnameTopping = lstnameTopping;
    }

    public String getPriceTopping() {
        return priceTopping;
    }

    public void setPriceTopping(String priceTopping) {
        this.priceTopping = priceTopping;
    }
}
