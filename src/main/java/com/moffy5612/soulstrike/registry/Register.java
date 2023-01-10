package com.moffy5612.soulstrike.registry;

import com.moffy5612.soulstrike.block.Blocks;
import com.moffy5612.soulstrike.item.Items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class Register {
    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event){
        IForgeRegistry<Block> registry = event.getRegistry();
        for(Block block : Blocks.regBlocks){
            block.setCreativeTab(CreativeTab.modTab);
            registry.register(block);
        }
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> registry = event.getRegistry();
        for(Item item : Items.regItems){
            item.setCreativeTab(CreativeTab.modTab);
            registry.register(item);
        }
        for(Item item : Items.regBlockItems){
            registry.register(item);
        }
        for(Item item : Items.regSoulItems){
            item.setCreativeTab(CreativeTab.modTab);
            registry.register(item);
        }
    }
}
