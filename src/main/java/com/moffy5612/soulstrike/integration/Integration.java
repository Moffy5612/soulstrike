package com.moffy5612.soulstrike.integration;

import com.moffy5612.soulstrike.SoulStrike;
import com.moffy5612.soulstrike.entity.bitn.EntityBitn;
import com.moffy5612.soulstrike.entity.bitn.ModelBitn;
import com.moffy5612.soulstrike.entity.bitn.RenderBitn;
import com.moffy5612.soulstrike.entity.capability.CapabilityBitnPlayer;
import com.moffy5612.soulstrike.integration.opencomputers.OpenComputersIntegration;
import com.moffy5612.soulstrike.integration.tconstruct.TConstructIntegration;
import com.moffy5612.soulstrike.item.Items;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Integration {
    public static void preInit(){

        Items.regItems.add(Items.ORB);
        Items.regItems.add(Items.MASTER_ORB);

        Items.regSoulItems.add(Items.HIDDEN_SHIKIMORI_STYLE);
        Items.regSoulItems.add(Items.PRIDE_OF_ROUND);
        Items.regSoulItems.add(Items.RECONSTRUCTION_CORE);
        Items.regSoulItems.add(Items.WILL_OF_STEEL);

        CapabilityBitnPlayer.register();

        if(SoulStrike.proxy.isClient())preInitClient();

        if(Loader.isModLoaded("tconstruct"))TConstructIntegration.preInit(); 
        if(Loader.isModLoaded("opencomputers"))OpenComputersIntegration.preInit();
    }

    public static void init(){
        if(Loader.isModLoaded("tconstruct"))TConstructIntegration.init(); 
    }

    public static void postInit(){
        if(Loader.isModLoaded("tconstruct"))TConstructIntegration.postInit();
    }
    
    @SideOnly(Side.CLIENT)
    public static void preInitClient(){
        RenderingRegistry.registerEntityRenderingHandler(EntityBitn.class, new IRenderFactory<EntityBitn>() {
            @Override
            public Render<? super EntityBitn> createRenderFor(RenderManager manager) {
                // TODO Auto-generated method stub
                return new RenderBitn(manager, new ModelBitn(), 0);
            }
        });
    }
}
