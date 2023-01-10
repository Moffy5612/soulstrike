package com.moffy5612.soulstrike.integration.opencomputers.items;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.integration.opencomputers.driver.DriverHiddenController;
import com.moffy5612.soulstrike.item.Items;

import ben_mkiv.openentity.common.items.OpenComputersItemBase;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import net.minecraft.item.ItemStack;

public class ItemHiddenController extends OpenComputersItemBase{
    
    public ItemHiddenController(){
        super(Reference.MOD_ID + ":hidden_controller", DriverHiddenController.class);
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        // TODO Auto-generated method stub
        if (stack.isEmpty())
            return false; 
        if (Items.HIDDEN_CONTROLLER.equals(stack.getItem())) {
            return true;
        }
        return false;
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        // TODO Auto-generated method stub
        if (worksWith(stack)) {
            return (ManagedEnvironment)new DriverHiddenController(host);
        }
        return null;
    }
}
