package com.progtech.etelrendelesapp.factory;

import com.progtech.etelrendelesapp.model.Menu;

public abstract class MenuFactory {
    public abstract Menu createMenu(String name);
}
