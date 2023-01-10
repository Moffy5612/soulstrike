package com.moffy5612.soulstrike.integration.tconstruct.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.moffy5612.soulstrike.integration.tconstruct.methods.ConArmMethods;
import com.moffy5612.soulstrike.integration.tconstruct.methods.SlashbladeTicMethods;
import com.moffy5612.soulstrike.integration.tconstruct.methods.TConstructMethods;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileReconstructedForge extends TileEntity implements ISidedInventory,ITickable{
    public NonNullList<ItemStack> inventory;
    public boolean canExtractMaterials;

    public TileReconstructedForge(){
        this.inventory = NonNullList.withSize(7, ItemStack.EMPTY);
        this.canExtractMaterials = false;
    }

    public void sync(){
		this.world.markAndNotifyBlock(pos, null, world.getBlockState(pos), world.getBlockState(pos), 2);
	}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub
        super.readFromNBT(compound);

        NBTTagList items = compound.getTagList("items", NBT.TAG_COMPOUND);
        for(int i = 0; i < items.tagCount(); i++){
            this.inventory.set(i, new ItemStack(items.getCompoundTagAt(i)));
        }

        this.canExtractMaterials = compound.getBoolean("canExtractMaterials");

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub

        NBTTagList items = new NBTTagList();
        for(int i = 0; i < this.inventory.size(); i++){
            NBTTagCompound item = new NBTTagCompound();
            ItemStack stack = this.inventory.get(i);
            stack.writeToNBT(item);
            items.appendTag(item);
        }

        compound.setTag("items", items);
        compound.setBoolean("canExtractMaterials", this.canExtractMaterials);

        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        // TODO Auto-generated method stub
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        // TODO Auto-generated method stub
        if(hasCapability(capability, facing))return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new TRFItemStackHandler(this));
        return super.getCapability(capability, facing);
    }

    @Override
    @Nullable
    public ITextComponent getDisplayName() {
        // TODO Auto-generated method stub
        return this.getDisplayName();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "tile.reconstructed_forge";
    }

    @Override
    public boolean hasCustomName() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getSizeInventory() {
        // TODO Auto-generated method stub
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        for(int i = 0; i < this.inventory.size(); i++){
            if(!this.inventory.get(i).isEmpty())return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        // TODO Auto-generated method stub
        return this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        // TODO Auto-generated method stub
        if(count < 1)return ItemStack.EMPTY;

        ItemStack stack = getStackInSlot(index);
		
		if(!stack.isEmpty()){
			if(stack.getCount() <= count){
				setInventorySlotContents(index, ItemStack.EMPTY);
			}else{
				stack = stack.splitStack(count);
				if(stack.getCount() == 0){
					setInventorySlotContents(index, ItemStack.EMPTY);
				}
			}
			this.markDirty();
		}
		
		return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        // TODO Auto-generated method stub
        ItemStack stack = this.inventory.get(index);
        this.setInventorySlotContents(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        // TODO Auto-generated method stub
        if(index < 0 || index >= inventory.size()) {
            return;
        }
        
        inventory.set(index, stack);
        
        if(!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        // TODO Auto-generated method stub
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        // TODO Auto-generated method stub
        if(world.getTileEntity(pos) != this || world.getBlockState(pos).getBlock() == Blocks.AIR) {
            return false;
        }
        return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // TODO Auto-generated method stub
        boolean flag = true;
        if(index == 6)return false;
        if(index < getSizeInventory()) {
            if(inventory.get(index).isEmpty() || stack.getCount() + inventory.get(index).getCount() <= getInventoryStackLimit()) {
                if(flag)return true;
            }
        }
        return false;
    }

    @Override
    public int getField(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getFieldCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        for(int i = 0; i < this.inventory.size(); i++){
            this.setInventorySlotContents(i, ItemStack.EMPTY);
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        // TODO Auto-generated method stub
        return new int[]{0, 1, 2, 3, 4, 5, 6};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        // TODO Auto-generated method stub
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        // TODO Auto-generated method stub
        if(index < 6)return false;
        return true;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        this.updateSlot();
        this.sync();
    }

    public void updateSlot(){
        boolean flag = false;
        if(!flag)flag = TConstructMethods.tryRepairTool(this);
        if(!flag)flag = TConstructMethods.tryModifyTool(this);
        if(!flag)flag = TConstructMethods.tryBuildTool(this);
        if(Loader.isModLoaded("conarm")){
            if(!flag)flag = ConArmMethods.tryRepairArmor(this);
            if(!flag)flag = ConArmMethods.tryModifyArmor(this);
            if(!flag)flag = ConArmMethods.tryBuildArmor(this);
        }
        if(Loader.isModLoaded("slashbladetic")){
            if(!flag)flag = SlashbladeTicMethods.tryRepairBlade(this);
            if(!flag)flag = SlashbladeTicMethods.tryModifyBlade(this);
            if(!flag)flag = SlashbladeTicMethods.tryBuildBlade(this);
        }
    }

    public class TRFItemStackHandler extends InvWrapper{
        public TileReconstructedForge trf;

        public TRFItemStackHandler(TileReconstructedForge trf){
            super(trf);
            this.trf = trf;
        }

        @Override
        @Nonnull
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            // TODO Auto-generated method stub
            ItemStack empty = ItemStack.EMPTY;
            if(empty != null){
                if(slot < 6 && !trf.canExtractMaterials)return empty;
            }
            return super.extractItem(slot, amount, simulate);
        }
    }
}
