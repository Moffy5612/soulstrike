package com.moffy5612.soulstrike.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemUtil {
    public static ItemBlock getItemInBlock(Block block){
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setCreativeTab(null);
        return itemBlock;
    }

    public static List<String> getTraits(ItemStack stack){
        List<String> traits = new ArrayList<String>();
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null){
            if(tag.hasKey("Traits")){
                NBTTagList list = tag.getTagList("Traits", NBT.TAG_STRING);
                for(int i = 0; i<list.tagCount(); i++)traits.add(list.getStringTagAt(i));
            }
        }
        return traits;
    }
}
