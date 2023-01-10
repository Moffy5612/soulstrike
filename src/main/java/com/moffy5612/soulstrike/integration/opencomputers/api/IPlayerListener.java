package com.moffy5612.soulstrike.integration.opencomputers.api;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public interface IPlayerListener {
    public void onEntityAttack(AttackEntityEvent event);
    public void onEntityDamaged(LivingAttackEvent event);
    public void onEntityDeath(LivingDeathEvent event);
}
