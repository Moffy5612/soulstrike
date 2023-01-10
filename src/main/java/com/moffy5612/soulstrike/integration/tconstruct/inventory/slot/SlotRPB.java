package com.moffy5612.soulstrike.integration.tconstruct.inventory.slot;

import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedPartBuilder;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.smeltery.ICast;
import slimeknights.tconstruct.library.tools.IPattern;

public class SlotRPB extends Slot{
    public TileReconstructedPartBuilder trpb;
    public InventoryPlayer inventoryPlayer;

    public SlotRPB(InventoryPlayer inventory, int index, int xPosition, int yPosition){
        super(inventory, index, xPosition, yPosition);
        
        this.inventoryPlayer = inventory;
    }

    public SlotRPB(TileReconstructedPartBuilder inventoryIn, int index, int xPosition, int yPosition) {
        //TODO Auto-generated constructor stub
        super(inventoryIn, index, xPosition, yPosition);
        this.trpb = inventoryIn;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        // TODO Auto-generated method stub
        if(this.slotNumber == 2)return false;
        else if(this.slotNumber == 0)return (stack.getItem() instanceof IPattern || stack.getItem() instanceof ICast);
        else return super.isItemValid(stack);
    }
}