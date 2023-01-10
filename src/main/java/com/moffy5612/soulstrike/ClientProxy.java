package com.moffy5612.soulstrike;

import java.util.List;

import com.moffy5612.soulstrike.entity.bitn.EntityBitn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ClientProxy extends CommonProxy{
    private static final int TOOLTIP_WRAP_WIDTH = 140;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":bitn"), EntityBitn.class, Reference.MOD_ID + ":bitn", 0, SoulStrike.instance, 64, 1, true);
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
	public String translate(String key, Style style, Object... args){
		return style.getFormattingCode() + I18n.format(key, args);
	}

	@Override
	public void addMultiLineDescription(List<String> tooltip, String key, Style style, Object... args){
		tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(translate(key, style, args), TOOLTIP_WRAP_WIDTH));
	}

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public EntityPlayer getEntityPlayer() {
        // TODO Auto-generated method stub
        return Minecraft.getMinecraft().player;
    }
}
