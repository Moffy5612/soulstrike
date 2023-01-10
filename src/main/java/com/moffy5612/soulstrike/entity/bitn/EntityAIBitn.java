package com.moffy5612.soulstrike.entity.bitn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EntityAIBitn extends EntityAIBase{
    public EntityBitn bitn;
    public EntityPlayer masterPlayer;
    public int index;

    public int tickCount;
    

    public EntityAIBitn(EntityBitn bitn){
        this.bitn = bitn;
        this.masterPlayer = null;
        this.index = -1;
        this.tickCount = 0;
    }

    @Override
    public boolean shouldExecute() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void startExecuting() {
        // TODO Auto-generated method stub
        this.masterPlayer = this.bitn.getMasterPlayer();
        this.index = this.bitn.index;
    }

    @Override
    public void resetTask() {
        this.masterPlayer = null;
        this.index = -1;
    }

    @Override
    public void updateTask() {
        double dx = 0, dy = 0, dz = 0;
        if(masterPlayer != null){
            double position[] = new double[5];
            Entity target = null;
            if(bitn.mode == BitnConstructor.Mode.SUPPORT){
                position = bitn.getPositionBehindMasterPlayer(masterPlayer, index);
                target = bitn.target;
                if(target != null){
                    dx = bitn.posX - target.posX;
                    dy = bitn.posY - target.posY;
                    dz = bitn.posZ - target.posZ;
                    if(index % 2 == 0)position[3] += Math.atan(dz / dx) / 2 / Math.PI * 360;
                    else position[3] -= Math.atan(dz / dx) / 2 / Math.PI * 360;
                    position[4] += Math.atan(dz / dy) / 2 / Math.PI * 360;
                }
                if(bitn.status == BitnConstructor.Status.WAIT_ATTACKING){
                    tickCount = (tickCount + 1) % (15 * (this.index + 1));
                    if(tickCount == 0)bitn.setStatus(BitnConstructor.Status.ATTACK);
                }
                if(bitn.status == BitnConstructor.Status.ATTACK){
                    tickCount = (tickCount + 1) % 2;
                    if(target != null){
                        if(tickCount == 1){
                            position[0] -= dx;
                            position[1] = position[1] - dy - 1;
                            position[2] -= dz;
                            if(target instanceof EntityLivingBase){
                                double damageAmount = this.bitn.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                                ItemStack equipment = this.bitn.equipment;
                                if(equipment != null)
                                    equipment.getItem().onLeftClickEntity(equipment, masterPlayer, target);
                                target.attackEntityFrom(DamageSource.causePlayerDamage(masterPlayer), (float)damageAmount);
                            }
                        }else if(tickCount == 0){
                            bitn.target = null;
                            bitn.setStatus(BitnConstructor.Status.STAY_BEHIND);
                        }
                    }
                }
                bitn.setRotationYawHead((float)position[3]);
                bitn.setRenderYawOffset((float)position[3]);    
                bitn.setPositionAndRotation(position[0], position[1], position[2], (float)position[3], (float)position[4]);
            }
        }else{
            this.masterPlayer = this.bitn.getMasterPlayer();
            this.index = this.bitn.index;
            this.bitn.setStatus(0);
        }
    }
}
