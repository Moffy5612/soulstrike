package com.moffy5612.soulstrike.render;

import com.moffy5612.soulstrike.item.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ItemRender {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        for(Item item : Items.regItems){
            register(item, 0);
        }
        for(Item item : Items.regBlockItems){
            register(item, 0);
        }
        for(Item item : Items.regSoulItems){
            register(item, 0);
        }
    }

    private static void register(Item item, int meta){
        ResourceLocation rl = item.getRegistryName();
        ModelResourceLocation mrl = new ModelResourceLocation(rl, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, mrl);
    }
}
