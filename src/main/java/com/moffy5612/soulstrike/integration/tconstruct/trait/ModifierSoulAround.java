package com.moffy5612.soulstrike.integration.tconstruct.trait;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.entity.capability.CapabilityBitnPlayer;
import com.moffy5612.soulstrike.utils.Element;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ModifierSoulAround extends ArmorModifierTrait{

    public ModifierSoulAround(){
        super(Reference.MOD_ID + ".soul_around", Element.getColorCode(Element.FIRE));
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        // TODO Auto-generated method stub
        boolean isElectrized = stack.hasCapability(CapabilityEnergy.ENERGY, null);
        boolean isChestPlate = EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST;
        return isElectrized && isChestPlate && super.canApplyCustom(stack);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        // TODO Auto-generated method stub
        if(player != null){
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(tool.hasCapability(CapabilityEnergy.ENERGY, null)){
                        IEnergyStorage ce = tool.getCapability(CapabilityEnergy.ENERGY, null);
                        if(ce != null){
                            if(cbp.isActivatedSupportMode){
                                int extracted = ce.extractEnergy(cbp.supportBitnList.size(), false);
                                if(extracted < 1)cbp.setActivatedAsSupportMode(false);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onArmorEquipped(ItemStack armor, EntityPlayer player, int slot) {
        if(player != null){
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(!cbp.consecutive){
                        cbp.setActivatedAsSupportMode(true);
                        cbp.consecutive = true;
                    }
                }
            }
        }
    }

    @Override
    public void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot) {
        // TODO Auto-generated method stub
        if(player != null){
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(player.inventory.armorItemInSlot(slot).equals(ItemStack.EMPTY)){
                        cbp.setActivatedAsSupportMode(false);
                        cbp.consecutive = false;
                    }
                }
            }
        } 
    }
}
