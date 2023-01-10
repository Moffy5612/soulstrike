package com.moffy5612.soulstrike.integration.opencomputers;

import com.moffy5612.soulstrike.integration.opencomputers.event.EntityEventHandler;
import com.moffy5612.soulstrike.integration.opencomputers.items.ItemBitnController;
import com.moffy5612.soulstrike.integration.opencomputers.items.ItemHiddenController;
import com.moffy5612.soulstrike.integration.tconstruct.trait.Traits;
import com.moffy5612.soulstrike.item.Items;

import c4.conarm.lib.utils.RecipeMatchHolder;
import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.DriverItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class OpenComputersIntegration {
    public static void preInit(){

        if(Loader.isModLoaded("openentity")){
            MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

            Items.HIDDEN_CONTROLLER = new ItemHiddenController();
            Items.BITN_CONTROLLER = new ItemBitnController();

            Driver.add((DriverItem)Items.HIDDEN_CONTROLLER);
            Driver.add((DriverItem)Items.BITN_CONTROLLER);

            Items.regItems.add(Items.HIDDEN_CONTROLLER);
            Items.regItems.add(Items.BITN_CONTROLLER);

            if(Loader.isModLoaded("tconevo") && Loader.isModLoaded("conarm")){
                Traits.regArmorModifiers.add(Traits.SOUL_CONNECTION);
    
                RecipeMatchHolder.addItem(Traits.SOUL_CONNECTION, Items.WILL_OF_STEEL);
            }
        }
    }
}
