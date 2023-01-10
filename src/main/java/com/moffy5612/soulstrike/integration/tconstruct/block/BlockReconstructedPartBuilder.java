package com.moffy5612.soulstrike.integration.tconstruct.block;

import javax.annotation.Nullable;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.SoulStrike;
import com.moffy5612.soulstrike.gui.SoulStrikeGuiList;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedForge;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedPartBuilder;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockReconstructedPartBuilder extends BlockContainer{

    public static final String NAME = "reconstructed_part_builder";

    public BlockReconstructedPartBuilder(){
        super(Material.WOOD);
        this.setRegistryName(Reference.MOD_ID, NAME);
        this.setUnlocalizedName(Reference.MOD_ID+":"+NAME);

        this.setSoundType(SoundType.WOOD);
        this.setResistance(5f);
        this.setHardness(1f);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.isSneaking()){
            TileReconstructedForge trpb = (TileReconstructedForge)worldIn.getTileEntity(pos);
            TextComponentString message = new TextComponentString(SoulStrike.proxy.translate(Reference.MOD_ID + ":canExtractMaterials", new Style().setColor(TextFormatting.WHITE)));
            if(trpb != null){
                trpb.canExtractMaterials = !trpb.canExtractMaterials;
                if(trpb.canExtractMaterials)message.appendText(SoulStrike.proxy.translate(Reference.MOD_ID + ":enable", new Style().setColor(TextFormatting.GREEN)));
                else message.appendText(SoulStrike.proxy.translate(Reference.MOD_ID + ":disable", new Style().setColor(TextFormatting.RED)));
                if(worldIn.isRemote)playerIn.sendMessage(message);
            }
        }else{
            playerIn.openGui(SoulStrike.instance, SoulStrikeGuiList.GUI_RECONSTRUCTED_PART_BUILDER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileReconstructedForge trpb = (TileReconstructedForge)worldIn.getTileEntity(pos);
        if(trpb != null){
            for(int i = 0; i < trpb.getSizeInventory(); i++){
                ItemStack stack = trpb.getStackInSlot(i);
                EntityItem entityItem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ());
                entityItem.setItem(stack);
                worldIn.spawnEntity(entityItem);
            }
        }
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        // TODO Auto-generated method stub
        return new TileReconstructedPartBuilder();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        // TODO Auto-generated method stub
        return EnumBlockRenderType.MODEL;
    }
}
