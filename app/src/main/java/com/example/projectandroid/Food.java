package com.example.projectandroid;

public class Food {
    String id;
        String img;
        String name;
        String mota;
        String sale;
        String path;

    public Food(String id, String img, String name, String mota, String sale, String path) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.mota = mota;
        this.sale = sale;
        this.path = path;
    }

    public String getId() {
        return id;
    }
    public String getPath() {
        return path;
    }

    public void setId(String id) {
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
}
