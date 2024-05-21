package com.progtech.etelrendelesapp.logger;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLogger {
    private static Logger logger = Logger.getLogger("AppLogger");

    static {
        try{
            FileHandler fileHandler = new FileHandler("ApplicationLog.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
        } catch (Exception e){
            logger.log(Level.SEVERE, "Hiba a logger setup-ba", e);
        }
    }
    public static void log(Level level, String message){
        logger.log(level, message);
    }
}
