package com.moffy5612.soulstrike.integration.tconstruct.trait;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.utils.Element;

import ben_mkiv.openentity.common.capability.energy.IenergyCapability;
import ben_mkiv.openentity.common.capability.energy.energyCapability;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ModifierSoulConnection extends ArmorModifierTrait{
    public ModifierSoulConnection(){
        super(Reference.MOD_ID+".soul_connection", Element.getColorCode(Element.LIGHT));
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        // TODO Auto-generated method stub
        boolean isElectrized = stack.hasCapability(CapabilityEnergy.ENERGY, null);
        boolean isHelmet = EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD;
        return isElectrized && isHelmet && super.canApplyCustom(stack);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        super.onArmorTick(tool, world, player);
        if(tool.hasCapability(CapabilityEnergy.ENERGY, null) && player.hasCapability(energyCapability.ENERGY, null)){
            IenergyCapability playerEnergy = player.getCapability(energyCapability.ENERGY, null);
            if(playerEnergy != null){
                int transferRate = playerEnergy.getStorage().getTransferrate();
                IEnergyStorage toolEnergy = tool.getCapability(CapabilityEnergy.ENERGY, null);
                if(toolEnergy != null){
                    int required = Math.min(transferRate, playerEnergy.getStorage().getMaxEnergyStored() - playerEnergy.getStorage().getEnergyStored());
                    int extracted = toolEnergy.extractEnergy(required, false);
                    playerEnergy.getStorage().receiveEnergy(extracted, false);
                };
            };
        }
    }
}