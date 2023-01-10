package com.moffy5612.soulstrike.entity.capability;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.entity.bitn.BitnConstructor;
import com.moffy5612.soulstrike.entity.bitn.EntityBitn;
import com.moffy5612.soulstrike.utils.ItemUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.Constants.NBT;

public class CapabilityBitnPlayer implements INBTSerializable<NBTTagCompound>{
    @CapabilityInject(CapabilityBitnPlayer.class)
    public static final Capability<CapabilityBitnPlayer> BITN_PLAYER = null;
    
    public boolean isActivatedSupportMode;
    public boolean consecutive;
    public EntityPlayer player;
    public List<EntityBitn> supportBitnList;

    public CapabilityBitnPlayer(){
        this(null);
    }

    public CapabilityBitnPlayer(EntityPlayer player){
        this.player = player;
        this.isActivatedSupportMode = false;
        this.supportBitnList = new ArrayList<EntityBitn>();
        this.consecutive = false;
    }

    public static void register(){
        CapabilityManager.INSTANCE.register(CapabilityBitnPlayer.class, new IStorage<CapabilityBitnPlayer>() {
            @Override
            public void readNBT(Capability<CapabilityBitnPlayer> capability, CapabilityBitnPlayer instance,
                    EnumFacing side, NBTBase nbt) {
                // TODO Auto-generated method stub
                
            }

            @Override
            @Nullable
            public NBTBase writeNBT(Capability<CapabilityBitnPlayer> capability, CapabilityBitnPlayer instance,
                    EnumFacing side) {
                // TODO Auto-generated method stub
                return null;
            }
        }, CapabilityBitnPlayer::new);
    }

    public void setActivatedAsSupportMode(boolean isActivated) {
        // TODO Auto-generated method stub
        if(!this.isActivatedSupportMode && isActivated)onSupportBitnActivated();
        else if(this.isActivatedSupportMode && !isActivated)onSupportBitnInactivated();
        this.isActivatedSupportMode = isActivated;
    }

    public void onSupportBitnActivated() {
        int n = 0;
        int xpLevel = player.experienceLevel;
        if(xpLevel < 20)n = 0;
        else if(xpLevel >= 20 && xpLevel < 40)n = 2;
        else n = 4;

        for(int i = 0; i < n; i++)this.supportBitnList.add(new EntityBitn(player.getEntityWorld()));

        // TODO Auto-generated method stub
        for(int i = 0; i < this.supportBitnList.size(); i++){
            EntityBitn bitn = this.supportBitnList.get(i);
            if(bitn != null){
                bitn.setMasterPlayer(player);
                bitn.index = i;
                bitn.equipment = null;
                bitn.status = BitnConstructor.Status.STAY_BEHIND;
                bitn.target = null;
                bitn.mode = BitnConstructor.Mode.SUPPORT;
                double position[] = bitn.getPositionBehindMasterPlayer(player, i);
                bitn.setPositionAndRotation(position[0], position[1], position[2], (float)position[3], (float)position[4]);
                player.getEntityWorld().spawnEntity(bitn);
            }
        }
    }

    public void onSupportBitnInactivated() {
        // TODO Auto-generated method stub
        int size = this.supportBitnList.size();
        for(int i = 0; i < size; i++){
            EntityBitn bitn = this.supportBitnList.get(0);
            if(bitn != null){
                bitn.returnEquipment();
                bitn.setDead();
                this.supportBitnList.remove(bitn);
            }
        }
    }

    public boolean canControlBitn(){
        NonNullList<ItemStack>armor = player.inventory.armorInventory;
        for(ItemStack stack : armor){
            if(ItemUtil.getTraits(stack).contains(Reference.MOD_ID + ".soul_around_armor"))return true;
        }
        return false;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        // TODO Auto-generated method stub
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound soulstrike = new NBTTagCompound();

        soulstrike.setBoolean("isActivated", this.isActivatedSupportMode); 
        if(this.supportBitnList.size() > 0){
            NBTTagList bitnsUUID = new NBTTagList();
            for(int i = 0; i < this.supportBitnList.size(); i++){
                bitnsUUID.appendTag(new NBTTagString(supportBitnList.get(i).getUniqueID().toString()));;
            }
            soulstrike.setTag("bitnUUID", bitnsUUID);
        }
        tag.setTag("soulstrike", soulstrike);
        
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        // TODO Auto-generated method stub
        if(nbt!=null){
            NBTTagCompound soulstrike = nbt.getCompoundTag("soulstrike");
            this.isActivatedSupportMode = soulstrike.getBoolean("isActivated");
            List<Entity> entities = player.world.getLoadedEntityList();
            if(soulstrike.hasKey("bitnUUID")){
                NBTTagList bitnsUUID = soulstrike.getTagList("bitnUUID", NBT.TAG_STRING);
                int i = 0;
                Iterator<NBTBase> iterator = bitnsUUID.iterator();
                while(iterator.hasNext()){
                    NBTTagString bitnUUIDTag = (NBTTagString)iterator.next();
                    for(Entity entity : entities){
                        if(entity.getUniqueID().toString().equals(bitnUUIDTag.getString())){
                            EntityBitn bitn = (EntityBitn)entity;
                            supportBitnList.add(bitn);
                            bitn.setMasterPlayer(player);
                            bitn.index = i;
                            i++;
                        }
                    }
                }
            }
            
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>{
        private CapabilityBitnPlayer capabilityBitnPlayer;
    
        public Provider(EntityPlayer player){
            capabilityBitnPlayer = new CapabilityBitnPlayer(player);
        }
    
        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            // TODO Auto-generated method stub
            return capability == BITN_PLAYER;
        }
    
        @Override
        @Nullable
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            // TODO Auto-generated method stub
            if(capability == BITN_PLAYER)return BITN_PLAYER.cast(capabilityBitnPlayer);
            return null;
        }
        
        @Override
        public NBTTagCompound serializeNBT() {
            // TODO Auto-generated method stub
            return capabilityBitnPlayer.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            // TODO Auto-generated method stub
            capabilityBitnPlayer.deserializeNBT(nbt);
        }
    }
}
