package com.moffy5612.soulstrike.integration.tconstruct.gui;

import javax.annotation.Nullable;

import com.moffy5612.soulstrike.gui.SoulStrikeGuiList;
import com.moffy5612.soulstrike.integration.tconstruct.inventory.ContainerReconstructedForge;
import com.moffy5612.soulstrike.integration.tconstruct.inventory.ContainerReconstructedPartBuilder;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedForge;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedPartBuilder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TConstructGuiHandler implements IGuiHandler{
    @Override
    @Nullable
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == SoulStrikeGuiList.GUI_RECONSTRUCTED_PART_BUILDER){
            TileReconstructedPartBuilder trpb = (TileReconstructedPartBuilder)world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerReconstructedPartBuilder(player.inventory, trpb);
        }else if(ID == SoulStrikeGuiList.GUI_RECONSTRUCTED_FORGE){
            TileReconstructedForge trf = (TileReconstructedForge)world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerReconstructedForge(player.inventory, trf);
        }
        return null;
    }

    @Override
    @Nullable
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        if(ID == SoulStrikeGuiList.GUI_RECONSTRUCTED_PART_BUILDER){
            TileReconstructedPartBuilder trpb = (TileReconstructedPartBuilder)world.getTileEntity(new BlockPos(x, y, z));
            return new GuiReconstructedPartBuilder(player.inventory, trpb);
        }else if(ID == SoulStrikeGuiList.GUI_RECONSTRUCTED_FORGE){
            TileReconstructedForge trf = (TileReconstructedForge)world.getTileEntity(new BlockPos(x, y, z));
            return new GuiReconstructedForge(player.inventory, trf);
        }
        return null;
    }
}
