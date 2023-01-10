package com.moffy5612.soulstrike.integration.tconstruct.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRF extends Slot{
    public SlotRF(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        // TODO Auto-generated method stub
        if(this.slotNumber == 6)return false;
        return super.isItemValid(stack);
    }
}
