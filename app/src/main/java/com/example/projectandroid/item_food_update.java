package com.example.projectandroid;

public class item_food_update {
    String id_food;
    int money;
    String name_food;
    String img_food;

    public item_food_update(int money, String name_food, String img_food) {
        this.money = money;
        this.name_food = name_food;
        this.img_food = img_food;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public String getImg_food() {
        return img_food;
    }

    public void setImg_food(String img_food) {
        this.img_food = img_food;
    }

}
