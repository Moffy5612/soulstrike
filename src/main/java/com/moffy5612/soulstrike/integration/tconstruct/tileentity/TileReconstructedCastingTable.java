package com.moffy5612.soulstrike.integration.tconstruct.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import slimeknights.tconstruct.smeltery.tileentity.TileCastingTable;

public class TileReconstructedCastingTable extends TileCastingTable{
    public TileReconstructedCastingTable(){
        super();
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if(!isStackInSlot(1) && this.tank.getFluidAmount() == 0)return index == 0;
        else return index == 1;
    }
}
