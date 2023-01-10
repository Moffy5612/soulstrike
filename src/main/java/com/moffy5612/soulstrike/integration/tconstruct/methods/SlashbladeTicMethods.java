package com.moffy5612.soulstrike.integration.tconstruct.methods;

import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedForge;

import cn.mmf.slashblade_tic.blade.TinkerSlashBladeRegistry;
import cn.mmf.slashblade_tic.util.SlashBladeBuilder;
import cn.mmf.slashblade_tic.util.SlashBladeHelper;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;

public class SlashbladeTicMethods {
    public static boolean tryRepairBlade(TileReconstructedForge trf){
        ItemStack toolStack = trf.getStackInSlot(0);
        NonNullList<ItemStack>materials = NonNullList.withSize(5, ItemStack.EMPTY);
        for(int i = 1; i < trf.getSizeInventory() - 1; i++)materials.set(i - 1, trf.getStackInSlot(i));
        if(!materials.isEmpty()){
            if(trf.inventory.get(6).isEmpty()){
                ItemStack result = SlashBladeBuilder.tryRepairTool(materials, toolStack, false);
                if(result != null && !result.isEmpty()){
                    result = SlashBladeBuilder.tryRepairTool(materials, toolStack, true);
                    if(result != null && !result.isEmpty()){
                        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(result);
                        if(tag != null)ItemSlashBlade.RepairCount.set(tag, ItemSlashBlade.RepairCount.get(tag) + 1);
                        float attack = SlashBladeHelper.getActualAttack(result);
                        ItemSlashBlade.setBaseAttackModifier(tag, attack);
                        trf.setInventorySlotContents(6, result);
                        trf.decrStackSize(0, 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean tryModifyBlade(TileReconstructedForge trf){
        ItemStack toolStack = trf.getStackInSlot(0);
        NonNullList<ItemStack>materials = NonNullList.withSize(5, ItemStack.EMPTY);
        for(int i = 1; i < trf.getSizeInventory() - 1; i++)materials.set(i - 1, trf.getStackInSlot(i));
        if(!materials.isEmpty()){
            try{
                if(trf.inventory.get(6).isEmpty()){
                    ItemStack result = SlashBladeBuilder.tryModifyTool(materials, toolStack, false);
                    if(result != null && !result.isEmpty()){
                        result = SlashBladeBuilder.tryModifyTool(materials, toolStack, true);
                        if(result != null && !result.isEmpty()){
                            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(result);
                            if(tag != null)ItemSlashBlade.RepairCount.set(tag, ItemSlashBlade.RepairCount.get(tag) + 1);
                            float attack = SlashBladeHelper.getActualAttack(result);
                            ItemSlashBlade.setBaseAttackModifier(tag, attack);
                            trf.setInventorySlotContents(6, result);
                            trf.decrStackSize(0, 1);
                            return true;
                        }
                    }
                }
            }catch(TinkerGuiException exception){}
        }
        return false;
    }

    public static boolean tryBuildBlade(TileReconstructedForge trf){
        NonNullList<ItemStack>materials = NonNullList.withSize(6, ItemStack.EMPTY);
        for(int i = 0; i < trf.getSizeInventory() - 1; i++)materials.set(i, trf.getStackInSlot(i));
        if(!materials.isEmpty()){
            if(trf.inventory.get(6).isEmpty()){
                ItemStack result = SlashBladeBuilder.tryBuildTool(materials, null, TinkerSlashBladeRegistry.getToolForgeCrafting());
                if(result != null && !result.isEmpty()){
                    NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(result);
                    if(tag != null)ItemSlashBlade.RepairCount.set(tag, ItemSlashBlade.RepairCount.get(tag) + 1);
                    float attack = SlashBladeHelper.getActualAttack(result);
                    ItemSlashBlade.setBaseAttackModifier(tag, attack);
                    trf.setInventorySlotContents(6, result);
                    for(int i = 0; i < trf.getSizeInventory() - 1; i++)trf.decrStackSize(i, 1);
                    return true;
                }
            }
        }
        return false;
    }
}
