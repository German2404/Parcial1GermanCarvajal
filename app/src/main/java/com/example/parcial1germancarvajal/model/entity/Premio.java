package com.example.parcial1germancarvajal.model.entity;

public class Premio {
    private int price;
    private String name;

    public Premio(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public Premio() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
