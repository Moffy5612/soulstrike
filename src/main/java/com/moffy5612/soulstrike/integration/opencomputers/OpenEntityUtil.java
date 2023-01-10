package com.moffy5612.soulstrike.integration.opencomputers;

import com.moffy5612.soulstrike.item.Items;

import ben_mkiv.openentity.common.capability.openComputer.IopenComputerCapability;
import ben_mkiv.openentity.common.capability.openComputer.openComputerRackCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class OpenEntityUtil {
    public static boolean hasBitnController(EntityPlayer player){
        if(player.hasCapability(openComputerRackCapability.OC_ENTITY, null)){
            IopenComputerCapability icc = player.getCapability(openComputerRackCapability.OC_ENTITY, null);
            if(icc != null){
                IInventory inventory = icc.mainInventory();
                for(int i = 0; i < inventory.getSizeInventory(); i++){
                    ItemStack stack = inventory.getStackInSlot(i);
                    if(stack.getItem().equals(Items.BITN_CONTROLLER)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
