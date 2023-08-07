package com.example.projectandroid;

public class Food_update {
        String img;
        String name;
        String mota;
        String sale;
        String id;

    public Food_update(String img, String name, String mota, String sale, String id) {
        this.img = img;
        this.name = name;
        this.mota = mota;
        this.sale = sale;
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
