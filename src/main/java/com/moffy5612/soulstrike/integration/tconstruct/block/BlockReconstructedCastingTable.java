package com.moffy5612.soulstrike.integration.tconstruct.block;

import com.google.common.collect.ImmutableList;
import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedCastingTable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.shared.block.BlockTable;
import slimeknights.tconstruct.smeltery.block.BlockCasting;

public class BlockReconstructedCastingTable extends BlockCasting{

    public static final String NAME = "reconstructed_casting_table";
    
    public BlockReconstructedCastingTable(){
        super();
        this.setRegistryName(Reference.MOD_ID, NAME);
        this.setUnlocalizedName(Reference.MOD_ID+":"+NAME);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, CastingType.TABLE.meta));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileReconstructedCastingTable();
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        ImmutableList<AxisAlignedBB> list = ImmutableList.of(
            new AxisAlignedBB(0, 0, 0, 1, 1, 1)
        );
        return BlockTable.raytraceMultiAABB(list, pos, start, end);
    }
}
