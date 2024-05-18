package com.progtech.etelrendelesapp.model;

public class BasicFood implements Menu {
    private String name;
    private int price;

    public BasicFood(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }
}

