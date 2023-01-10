package com.moffy5612.soulstrike.integration.tconstruct.event;
import com.moffy5612.soulstrike.entity.bitn.BitnConstructor;
import com.moffy5612.soulstrike.entity.bitn.EntityBitn;
import com.moffy5612.soulstrike.entity.capability.CapabilityBitnPlayer;
import com.moffy5612.soulstrike.integration.opencomputers.OpenEntityUtil;
import com.moffy5612.soulstrike.item.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoulAroundEvent {
    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event){
        EntityPlayer player = event.getEntityPlayer();
        if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
            CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
            if(cbp != null){
                if(cbp.isActivatedSupportMode){
                    if(Items.BITN_CONTROLLER != null){
                        if(OpenEntityUtil.hasBitnController(player))return;
                    }
                    for(EntityBitn bitn : cbp.supportBitnList){
                        if(bitn != null){
                            bitn.target = event.getTarget();
                            bitn.setStatus(BitnConstructor.Status.WAIT_ATTACKING);  
                        }
                    }
                }
            }
        }
    }
}
