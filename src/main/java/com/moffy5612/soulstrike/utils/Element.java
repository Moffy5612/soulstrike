package com.moffy5612.soulstrike.utils;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class Element {
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int FOREST = 3;
    public static final int LIGHT = 4;
    public static final int DARK = 5;

    public static Style getColorStyle(int element){
        if(element == Element.FIRE)return new Style().setColor(TextFormatting.DARK_RED);
        else if(element == Element.WATER) return new Style().setColor(TextFormatting.AQUA);
        else if(element == Element.FOREST) return new Style().setColor(TextFormatting.GREEN);
        else if(element == Element.LIGHT) return new Style().setColor(TextFormatting.YELLOW);
        else if(element == Element.DARK) return new Style().setColor(TextFormatting.LIGHT_PURPLE);
        return null;
    }

    public static int getColorCode(int element){
        if(element == Element.FIRE)return 0xbf382e;
        else if(element == Element.WATER) return 0x6ee4eb;
        else if(element == Element.FOREST) return 0x049107;
        else if(element == Element.LIGHT) return 0xe8eb34;
        else if(element == Element.DARK) return 0xde07d3;
        return -1;
    }
}
