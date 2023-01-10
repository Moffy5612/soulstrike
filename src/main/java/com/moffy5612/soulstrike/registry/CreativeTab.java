package com.moffy5612.soulstrike.registry;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.item.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab{
    public static ModTab modTab = new ModTab();
}

class ModTab extends CreativeTabs{
    public ModTab(){
        super(Reference.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.ORB);
    }
}