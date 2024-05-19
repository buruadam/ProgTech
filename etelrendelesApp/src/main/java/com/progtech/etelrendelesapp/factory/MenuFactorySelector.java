package com.progtech.etelrendelesapp.factory;

import java.util.HashMap;
import java.util.Map;

public class MenuFactorySelector {
    private static final Map<String, MenuFactory> factories = new HashMap<>();

    static {
        registerFactory("pizza", new PizzaFactory());
        registerFactory("hamburger", new HamburgerFactory());
        registerFactory("drink", new DrinkFactory());
    }

    public static void registerFactory(String tpye, MenuFactory factory){
        factories.put(tpye.toLowerCase(), factory);
    }

    public static MenuFactory getFactory(String type){
        MenuFactory factory = factories.get(type.toLowerCase());
        if (factory == null)
            throw new IllegalArgumentException("Ismeretlen menü típus: " + type);
        return factory;
    }

}
