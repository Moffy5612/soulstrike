package com.moffy5612.soulstrike.event;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.entity.capability.CapabilityBitnPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityEvent 
{
    @SubscribeEvent
    public static void onAttachEntity(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof EntityPlayer){
            event.addCapability(new ResourceLocation(Reference.MOD_ID + ":bitn_player"), new CapabilityBitnPlayer.Provider((EntityPlayer)event.getObject()));
        }
    }
}
