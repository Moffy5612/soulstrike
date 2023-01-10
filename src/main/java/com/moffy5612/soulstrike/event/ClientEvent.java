package com.moffy5612.soulstrike.event;

import com.moffy5612.soulstrike.event.modelbake.IModelBakeEvents;
import com.moffy5612.soulstrike.event.modelbake.ModelBakeEvents;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientEvent {
  
  @SubscribeEvent
  public static void onModelBake(ModelBakeEvent event) {
		for(IModelBakeEvents e : ModelBakeEvents.modelBakeEvents){
            e.runEvent(event);
        }
	}
}
