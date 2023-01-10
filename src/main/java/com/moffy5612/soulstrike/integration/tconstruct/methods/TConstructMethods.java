package com.moffy5612.soulstrike.integration.tconstruct.methods;

import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedForge;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.utils.ToolBuilder;

public class TConstructMethods {
    public static boolean tryRepairTool(TileReconstructedForge trf){
        ItemStack toolStack = trf.getStackInSlot(0);
        NonNullList<ItemStack>materials = NonNullList.withSize(5, ItemStack.EMPTY);
        for(int i = 1; i < trf.getSizeInventory() - 1; i++)materials.set(i - 1, trf.getStackInSlot(i));
        if(!materials.isEmpty()){
            if(trf.inventory.get(6).isEmpty()){
                ItemStack result = ToolBuilder.tryRepairTool(materials, toolStack, false);
                if(result != null && !result.isEmpty()){
                    result = ToolBuilder.tryRepairTool(materials, toolStack, true);
                    if(result != null && !result.isEmpty())trf.setInventorySlotContents(6, result);
                    trf.decrStackSize(0, 1);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean tryModifyTool(TileReconstructedForge trf){
        ItemStack toolStack = trf.getStackInSlot(0);
        NonNullList<ItemStack>materials = NonNullList.withSize(5, ItemStack.EMPTY);
        for(int i = 1; i < trf.getSizeInventory() - 1; i++)materials.set(i - 1, trf.getStackInSlot(i));
        if(!materials.isEmpty()){
            try{
                if(trf.inventory.get(6).isEmpty()){
                    ItemStack result = ToolBuilder.tryModifyTool(materials, toolStack, false);
                    if(result != null && !result.isEmpty()){
                        result = ToolBuilder.tryModifyTool(materials, toolStack, true);
                        if(result != null && !result.isEmpty())trf.setInventorySlotContents(6, result);
                        trf.decrStackSize(0, 1);
                        return true;
                    }
                }
            }catch(TinkerGuiException exception){}
        }
        return false;
    }

    public static boolean tryBuildTool(TileReconstructedForge trf){
        NonNullList<ItemStack>materials = NonNullList.withSize(6, ItemStack.EMPTY);
        for(int i = 0; i < trf.getSizeInventory() - 1; i++)materials.set(i, trf.getStackInSlot(i));
        if(!materials.isEmpty()){
            if(trf.inventory.get(6).isEmpty()){
                ItemStack result = ToolBuilder.tryBuildTool(materials, null);
                if(result != null && !result.isEmpty()){
                    trf.setInventorySlotContents(6, result);
                    for(int i = 0; i < trf.getSizeInventory() - 1; i++)trf.decrStackSize(i, 1);
                    return true;
                }
            }
        }
        return false;
    }
}
