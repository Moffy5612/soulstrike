package com.moffy5612.soulstrike.item;

import java.util.ArrayList;
import java.util.List;

import com.moffy5612.soulstrike.integration.opencomputers.items.ItemBitnController;
import com.moffy5612.soulstrike.integration.opencomputers.items.ItemHiddenController;
import com.moffy5612.soulstrike.utils.Element;

import net.minecraft.item.Item;

public class Items {
    public static final ItemOrb ORB = new ItemOrb(false);
    public static final ItemOrb MASTER_ORB = new ItemOrb(true);

    public static final ItemSoul HIDDEN_SHIKIMORI_STYLE = new ItemSoul("hidden_shikimori_style", Element.FOREST);
    public static final ItemSoul PRIDE_OF_ROUND = new ItemSoul("pride_of_round", Element.FIRE);
    public static final ItemSoul RECONSTRUCTION_CORE = new ItemSoul("reconstruction_core", Element.WATER);
    public static final ItemSoul WILL_OF_STEEL = new ItemSoul("will_of_steel", Element.LIGHT);
    
    public static ItemHiddenController HIDDEN_CONTROLLER;
    public static ItemBitnController BITN_CONTROLLER;

    public static List<Item> regBlockItems = new ArrayList<Item>();
    public static List<Item> regItems = new ArrayList<Item>();
    public static List<Item> regSoulItems = new ArrayList<Item>();
}
