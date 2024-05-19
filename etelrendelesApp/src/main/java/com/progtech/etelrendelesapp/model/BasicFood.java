package com.progtech.etelrendelesapp.model;

import java.util.ArrayList;
import java.util.List;

public class BasicFood implements Menu {
    private String name;
    private int price;
    private List<String> toppings = new ArrayList<>();

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
    @Override
    public void setPrice(int price){
        this.price = price;
    }

    @Override
    public void addTopping(String topping) {
        toppings.add(topping);
    }

    @Override
    public String getToppings() {
        return String.join(", ", toppings);
    }
}

