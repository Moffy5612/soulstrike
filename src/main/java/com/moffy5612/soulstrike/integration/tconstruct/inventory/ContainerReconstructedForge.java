package com.moffy5612.soulstrike.integration.tconstruct.inventory;

import com.moffy5612.soulstrike.integration.tconstruct.inventory.slot.SlotRF;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedForge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReconstructedForge extends Container{

    public TileReconstructedForge trf;

    public ContainerReconstructedForge(InventoryPlayer playerInventory, TileReconstructedForge trf){
        this.trf = trf;

        this.addSlotToContainer(new SlotRF(trf, 0, 33, 42));
        this.addSlotToContainer(new SlotRF(trf, 1, 15, 63));
        this.addSlotToContainer(new SlotRF(trf, 2, 11, 38));
        this.addSlotToContainer(new SlotRF(trf, 3, 33, 20));
        this.addSlotToContainer(new SlotRF(trf, 4, 55, 38));
        this.addSlotToContainer(new SlotRF(trf, 5, 51, 63));
		this.addSlotToContainer(new SlotRF(trf, 6, 124, 38));

        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new SlotRF(playerInventory, i * 9 + j + 9, 8 + j * 18, 92 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlotToContainer(new SlotRF(playerInventory, k, 8 + k * 18, 150));
		}
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index){
		ItemStack remainder = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stackSlot = slot.getStack();
			remainder = stackSlot.copy();

			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
			if (index < containerSlots) {
				if (!this.mergeItemStack(stackSlot, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(stackSlot, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}

			if(stackSlot.getCount() == 0){
				slot.putStack(ItemStack.EMPTY);
			}else{
				slot.onSlotChanged();
			}

			if(stackSlot.getCount() == remainder.getCount()){
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stackSlot);
		}
		return remainder;
	}
}
