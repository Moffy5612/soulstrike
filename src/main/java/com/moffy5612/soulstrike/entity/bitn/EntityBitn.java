package com.moffy5612.soulstrike.entity.bitn;

import javax.annotation.Nullable;

import com.moffy5612.soulstrike.entity.capability.CapabilityBitnPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class EntityBitn extends EntityLiving{
    public final double BITN_RELATICVE_COORDINATES[][] = {
        {0.5, 0.2, -0.5},
        {-0.9, 0.2, -0.5},
        {1.2, 0.5, -0.3},
        {-1.6, 0.5, -0.3},
    };

    public double tickCount;
    public int mode;
    public int status;
    @Nullable public Entity target;
    public EntityPlayer masterPlayer;
    public int index;
    @Nullable public ItemStack equipment;

    public EntityBitn(World worldIn){
        super(worldIn);
        this.tickCount = 0;
        this.status = 0;
        this.target = null;
        this.mode = BitnConstructor.Mode.GUARD;
        
        this.tasks.taskEntries.clear();
        this.tasks.addTask(0, new EntityAIBitn(this));
        
        this.setNoGravity(true);
    }

    public EntityPlayer getMasterPlayer(){
        return this.masterPlayer;
    }

    public void setMasterPlayer(EntityPlayer masterPlayer){
        this.masterPlayer = masterPlayer;
    }

    public void setEquipment(ItemStack stack){
        this.equipment = stack;
        this.setHeldItem(EnumHand.MAIN_HAND, stack);
    }

    public void setMode(int mode){
        this.mode = mode;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public void returnEquipment(){
        if(this.equipment != null){
            if(!this.masterPlayer.inventory.addItemStackToInventory(this.equipment))this.masterPlayer.dropItem(equipment, false, false);
            this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
            this.equipment = null;
        }
    }

    public void returnEquipment(int slot){
        if(this.equipment != null){
            if(this.masterPlayer.inventory.getStackInSlot(slot).equals(ItemStack.EMPTY)){
                this.masterPlayer.inventory.add(slot, this.equipment);
                this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                this.equipment = null;
            }else{
                returnEquipment();
            }
        }
    }

    public void returnEquipment(ItemStackHandler inventory, int slot){
        ItemStack equipment = this.equipment;
        if(equipment != null){
            if(inventory.getStackInSlot(slot).equals(ItemStack.EMPTY)){
                inventory.insertItem(slot, equipment, false);
                this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                this.equipment = null;
            }else{
                int i = 0;
                while(!inventory.insertItem(i, equipment, true).equals(equipment)){
                    i++;
                    if(i + 1 >= inventory.getSlots()){
                        this.masterPlayer.dropItem(equipment, false, false);
                        return;
                    };
                }
                inventory.insertItem(i, equipment, false);
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        // TODO Auto-generated method stub
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }



    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canBeHitWithPotion() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canBePushed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean canEquipItem(ItemStack stack) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {}

    @Override
    protected void collideWithNearbyEntities() {}

    @Override
    public void onKillCommand() {
        this.setDead();
    }

    @Override
    public void onDeath(DamageSource cause) {
            this.returnEquipment();
            EntityPlayer player = this.getMasterPlayer();
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    cbp.supportBitnList.remove(this);
                }
            }
        super.onDeath(cause);
    }

    public double[] getPositionBehindMasterPlayer(EntityPlayer player, int index){
        this.tickCount = (this.tickCount + 1) % 360;

        double x = player.posX + BITN_RELATICVE_COORDINATES[index][0]; 
        double y = player.posY + BITN_RELATICVE_COORDINATES[index][1];
        double z = player.posZ + BITN_RELATICVE_COORDINATES[index][2];
        double yaw = player.getPitchYaw().y / 360 * 2 * Math.PI;
        double centerX, centerZ;

        y += 0.05 * Math.sin(this.tickCount / 360 * 18 * Math.PI + index * Math.PI / 2);
        
        centerX = player.posX - 0.2D;
        centerZ = player.posZ;
        double tmpX = x, tmpZ = z;
        x = Math.cos(yaw) * tmpX - Math.sin(yaw) * tmpZ - Math.cos(yaw) * centerX + Math.sin(yaw) * centerZ + centerX;
        z = Math.sin(yaw) * tmpX + Math.cos(yaw) * tmpZ - Math.sin(yaw) * centerX - Math.cos(yaw) * centerZ + centerZ;

        return new double[]{x, y, z, player.rotationYaw, player.rotationPitch};
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub
        super.readFromNBT(compound);
        if(compound != null){
            NBTTagCompound soulstrike = compound.getCompoundTag("soulstrike");
            
            this.masterPlayer = this.world.getPlayerEntityByName(soulstrike.getString("masterName"));
            this.index = soulstrike.getInteger("index");

            if(soulstrike.hasKey("equipment")){
                this.setEquipment(new ItemStack(soulstrike.getCompoundTag("equipment")));
            }

            this.mode = soulstrike.getInteger("bitnMode");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        // TODO Auto-generated method stub
        NBTTagCompound tag = super.writeToNBT(compound);
        NBTTagCompound soulstrike = new NBTTagCompound();

        soulstrike.setString("masterName", this.masterPlayer.getName());
        soulstrike.setInteger("index", this.index);


        ItemStack equipment = this.equipment;
        if(equipment != null){
            soulstrike.setTag("equipment", equipment.serializeNBT());
        }

        soulstrike.setInteger("bitnMode", this.mode);

        tag.setTag("soulstrike", soulstrike);
        return tag;
    }
}
