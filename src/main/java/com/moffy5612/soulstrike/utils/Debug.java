package com.moffy5612.soulstrike.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Debug {
    private static Logger logger;

    public static void init(FMLPreInitializationEvent event){
        logger = event.getModLog();
    }

    public static void log(String message){
        log(message, 1);
    }

    public static void log(Boolean b){
        log(b, 1);
    }

    public static void log(int i){
        log(i, 1);
    }

    public static void log(double d){
        log(d, 1);
    }

    public static void log(String message, int count){
        for(int i = 0; i < count; i++)logger.log(Level.OFF, message);
    }

    public static void log(Boolean b, int count){
        log(Boolean.toString(b), count);
    }

    public static void log(int i, int count){
        log(Integer.toString(i), count);
    }

    public static void log(double d, int count){
        log(Double.toString(d), count);
    }
}
