package com.example.projectandroid;

public class Topping {
    String name;
    int giatien;

    public Topping(String name, int giatien) {
        this.name = name;
        this.giatien = giatien;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGiatien() {
        return giatien;
    }

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }
}
