package com.progtech.etelrendelesapp.model;

public interface Menu {
    String getName();
    int getPrice();
    void setPrice(int price);
    void addTopping(String topping);
    String getToppings();
}

