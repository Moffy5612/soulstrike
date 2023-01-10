package com.moffy5612.soulstrike;

import java.util.List;

import com.moffy5612.soulstrike.integration.Integration;
import com.moffy5612.soulstrike.utils.Debug;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event){
      Debug.init(event);
      Integration.preInit();
    }

    public void init(FMLInitializationEvent event){
      Integration.init();
    }

    public void postInit(FMLPostInitializationEvent event){
      Integration.postInit();
    }

    public void addMultiLineDescription(List<String> tooltip, String key, Style style, Object... args){}

    public void addMultiLineDescription(List<String> tooltip, String key, Object... args){
      this.addMultiLineDescription(tooltip, key, new Style().setColor(TextFormatting.GRAY), args);
    }

    public String translate(String key, Object... args){
		return translate(key, new Style(), args);
	}

    public String translate(String key, Style style, Object... args){
		return key;
	}

    public boolean isClient(){
      return false;
    }

    public EntityPlayer getEntityPlayer() {return null;}
}
