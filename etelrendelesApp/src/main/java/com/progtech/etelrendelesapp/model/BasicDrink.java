package com.progtech.etelrendelesapp.model;

public class BasicDrink implements Menu{

    private String name;
    private int price;

    public BasicDrink(String name, int price) {
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
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void addTopping(String topping) {

    }

    @Override
    public String getToppings() {
        return "";
    }
}
