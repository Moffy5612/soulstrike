package com.moffy5612.soulstrike.integration.opencomputers.event;

import com.moffy5612.soulstrike.integration.opencomputers.api.IPlayerListener;
import com.moffy5612.soulstrike.integration.opencomputers.api.PlayerListeners;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler {

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event){
        for(IPlayerListener listener : PlayerListeners.listeners){
            listener.onEntityAttack(event);
        }
    }

    @SubscribeEvent
    public void onEntityDamaged(LivingAttackEvent event){
        if(event.getEntity() instanceof EntityPlayer){
            for(IPlayerListener listener : PlayerListeners.listeners){
                listener.onEntityDamaged(event);
            }
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event){
        if(event.getEntity() instanceof EntityPlayer){
            for(IPlayerListener listener : PlayerListeners.listeners){
                listener.onEntityDeath(event);
            }
        }
    }
}
