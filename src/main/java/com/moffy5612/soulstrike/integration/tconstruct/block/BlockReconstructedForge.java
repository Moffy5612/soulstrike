package com.moffy5612.soulstrike.integration.tconstruct.block;

import javax.annotation.Nullable;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.SoulStrike;
import com.moffy5612.soulstrike.gui.SoulStrikeGuiList;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedForge;

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

public class BlockReconstructedForge extends BlockContainer{
    public static final String NAME = "reconstructed_forge";

    public BlockReconstructedForge(){
        super(Material.IRON);
        this.setRegistryName(Reference.MOD_ID, NAME);
        this.setUnlocalizedName(Reference.MOD_ID+":"+NAME);

        this.setSoundType(SoundType.METAL);
        this.setResistance(10f);
        this.setHardness(2f);

        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.isSneaking()){
            TileReconstructedForge trf = (TileReconstructedForge)worldIn.getTileEntity(pos);
            TextComponentString message = new TextComponentString(SoulStrike.proxy.translate(Reference.MOD_ID + ":canExtractMaterials", new Style().setColor(TextFormatting.WHITE)));
            if(trf != null){
                trf.canExtractMaterials = !trf.canExtractMaterials;
                if(trf.canExtractMaterials)message.appendText(SoulStrike.proxy.translate(Reference.MOD_ID + ":enable", new Style().setColor(TextFormatting.GREEN)));
                else message.appendText(SoulStrike.proxy.translate(Reference.MOD_ID + ":disable", new Style().setColor(TextFormatting.RED)));
                if(worldIn.isRemote)playerIn.sendMessage(message);
            }
        }else{
            playerIn.openGui(SoulStrike.instance, SoulStrikeGuiList.GUI_RECONSTRUCTED_FORGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileReconstructedForge trf = (TileReconstructedForge)worldIn.getTileEntity(pos);
        if(trf != null){
            for(int i = 0; i < trf.getSizeInventory(); i++){
                ItemStack stack = trf.getStackInSlot(i);
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
        return new TileReconstructedForge();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        // TODO Auto-generated method stub
        return EnumBlockRenderType.MODEL;
    }
}
