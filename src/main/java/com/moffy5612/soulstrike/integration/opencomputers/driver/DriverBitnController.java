package com.moffy5612.soulstrike.integration.opencomputers.driver;

import java.util.ArrayList;
import java.util.List;

import com.moffy5612.soulstrike.entity.bitn.BitnConstructor;
import com.moffy5612.soulstrike.entity.bitn.EntityBitn;
import com.moffy5612.soulstrike.entity.capability.CapabilityBitnPlayer;

import ben_mkiv.openentity.common.capability.customInventory.IcustomInventoryCapability;
import ben_mkiv.openentity.common.capability.customInventory.customInventoryCapability;
import ben_mkiv.openentity.common.capability.energy.IenergyCapability;
import ben_mkiv.openentity.common.capability.energy.energyCapability;
import ben_mkiv.openentity.common.component.OpenEntityComputerComponent;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

public class DriverBitnController extends OpenEntityComputerComponent{
    public DriverBitnController(EnvironmentHost host){
        setupHost("bitn_controller", host);
        setupNode();
    }

    private boolean consumeEnergy(){
        return consumeEnergy(1);
    }

    private boolean consumeEnergy(int range){
        if(this.entity.hasCapability(energyCapability.ENERGY, null)){
            IenergyCapability ec = this.entity.getCapability(energyCapability.ENERGY, null);
            if(ec != null){
                return ec.getStorage().extractEnergy(10*range, false) != 0;
            }
        }
        return false;
    }

    @Callback(doc = "function(useCustomInventory:boolean, slotId:number, bitnIndex:number):boolean, string; Equip Bit'n with the ItemStack in that slot.", direct = true)
    public Object[] equipItem(Context context, Arguments args){
        if(this.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.entity;
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(cbp.isActivatedSupportMode){
                        if(args.checkBoolean(0)){
                            if(player.hasCapability(customInventoryCapability.INVENTORY, null)){
                                IcustomInventoryCapability icic = player.getCapability(customInventoryCapability.INVENTORY, null);
                                if(icic != null){
                                    if(args.checkInteger(1) > 0 && icic.getInventory().getInventorySlotCount() >= args.checkInteger(1)){
                                        ItemStack target = icic.getInventory().getStackInSlot(args.checkInteger(1) - 1);
                                        if(args.checkInteger(2) > 0 && cbp.supportBitnList.size() >= args.checkInteger(2)){
                                            EntityBitn bitn = cbp.supportBitnList.get(args.checkInteger(2) - 1);
                                            if(bitn != null){
                                                if(consumeEnergy()){
                                                    icic.getInventory().extractItem(args.checkInteger(1) - 1, target.getCount(), false);
                                                    bitn.setEquipment(target);
                                                    return new Object[]{true, target.getDisplayName()};
                                                }else{
                                                    return new Object[]{false, "Not enough energy."};
                                                }
                                            }else{
                                                return new Object[]{false, "Bit'n of that Index does not exist."};
                                            }
                                        }else{
                                            return new Object[]{false, "Bit'n of that Index does not exist."};
                                        }
                                    }else{
                                        return new Object[]{false, "slotId exceeds maximum inventory size."};
                                    }
                                }else{
                                    return new Object[]{false, "Inventory Interface is not installed."};
                                }
                            }else{
                                return new Object[]{false, "Inventory Interface is not installed."};
                            }
                        }else{
                            InventoryPlayer inventory = player.inventory;
                            if(args.checkInteger(1) > 0 && inventory.getSizeInventory() >= args.checkInteger(1)){
                                ItemStack target = inventory.getStackInSlot(args.checkInteger(1) - 1);
                                if(args.checkInteger(2) > 0 && cbp.supportBitnList.size() >= args.checkInteger(2)){
                                    EntityBitn bitn = cbp.supportBitnList.get(args.checkInteger(2) - 1);
                                    if(bitn != null){
                                        if(consumeEnergy()){
                                            inventory.removeStackFromSlot(args.checkInteger(1) - 1);
                                            bitn.setEquipment(target);
                                            return new Object[]{true, target.getDisplayName()};
                                        }else{
                                            return new Object[]{false, "Not enough energy."};
                                        }
                                    }else{
                                        return new Object[]{false, "Bit'n of that Index does not exist."};
                                    }
                                }else{
                                    return new Object[]{false, "Bit'n of that Index does not exist."};
                                }
                            }else{
                                return new Object[]{false, "slotId exceeds maximum inventory size."};
                            }
                        }
                    }else{
                        return new Object[]{false, "Bit'n are not activated."};
                    }
                }
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Caller is not a player."};
        }
    }

    @Callback(doc = "function():boolean, string; Activate Bit'n.", direct = true)
    public Object[] activate(Context context, Arguments args){
        if(this.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.entity;
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(cbp.canControlBitn()){
                        if(consumeEnergy()){
                            cbp.setActivatedAsSupportMode(true);
                            return new Object[]{true, "Bit'n has been successfully activated."};
                        }else{
                            return new Object[]{false, "Not enough energy."};
                        }
                        
                    }else{
                        return new Object[]{false, "A chestplate with the specific modifier is required to control Bit'n."};
                    }
                }
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Caller is not a player."};
        }
    }

    @Callback(doc = "function():boolean, string; Sleep Bit'n.", direct = true)
    public Object[] sleep(Context context, Arguments args){
        if(this.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.entity;
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(cbp.canControlBitn()){
                        cbp.setActivatedAsSupportMode(false);
                        return new Object[]{true, "Bit'n has been successfully put to sleeps."};
                    }else{
                        return new Object[]{false, "A chestplate with the modifier \"Soul-Around\" is required to control Bit'n."};
                    }
                }
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Caller is not a player."};
        }
    }

    @Callback(doc = "function(useCustomInventory:boolean, slotId:number, bitnIndex:number):boolean, string; Returns Bit'n's equipment to the player.", direct = true)
    public Object[] returnEquipment(Context context, Arguments args){
        if(this.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.entity;
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(cbp.isActivatedSupportMode){
                        if(args.checkBoolean(0)){
                            if(player.hasCapability(customInventoryCapability.INVENTORY, null)){
                                IcustomInventoryCapability icic = player.getCapability(customInventoryCapability.INVENTORY, null);
                                if(icic != null){
                                    if(args.checkInteger(1) > 0 && icic.getInventory().getInventorySlotCount() >= args.checkInteger(1)){
                                        ItemStack target = icic.getInventory().getStackInSlot(args.checkInteger(1) - 1);
                                        if(args.checkInteger(2) > 0 && cbp.supportBitnList.size() >= args.checkInteger(2)){
                                            EntityBitn bitn = cbp.supportBitnList.get(args.checkInteger(2) - 1);
                                            if(bitn != null){
                                                if(consumeEnergy()){
                                                    bitn.returnEquipment(icic.getInventory(), args.checkInteger(1) - 1);
                                                    return new Object[]{true, target.getDisplayName()};
                                                }else{
                                                    return new Object[]{false, "Not enough energy."};
                                                } 
                                            }else{
                                                return new Object[]{false, "Bit'n of that Index does not exist."};
                                            }
                                        }else{
                                            return new Object[]{false, "Bit'n of that Index does not exist."};
                                        }
                                    }else{
                                        return new Object[]{false, "slotId exceeds maximum inventory size."};
                                    }
                                }else{
                                    return new Object[]{false, "Inventory Interface is not installed."};
                                }
                            }else{
                                return new Object[]{false, "Inventory Interface is not installed."};
                            }
                        }else{
                            InventoryPlayer inventory = player.inventory;
                            if(args.checkInteger(1) > 0 && inventory.getSizeInventory() >= args.checkInteger(1)){
                                ItemStack target = inventory.getStackInSlot(args.checkInteger(1) - 1);
                                if(args.checkInteger(2) > 0 && cbp.supportBitnList.size() >= args.checkInteger(2)){
                                    EntityBitn bitn = cbp.supportBitnList.get(args.checkInteger(2) - 1);
                                    if(bitn != null){
                                        if(consumeEnergy()){
                                            bitn.returnEquipment(args.checkInteger(1) - 1);
                                            return new Object[]{true, target.getDisplayName()};
                                        }else{
                                            return new Object[]{false, "Not enough energy."};
                                        }
                                    }else{
                                        return new Object[]{false, "Bit'n of that Index does not exist."};
                                    }
                                }else{
                                    return new Object[]{false, "Bit'n of that Index does not exist."};
                                }
                            }else{
                                return new Object[]{false, "slotId exceeds maximum inventory size."};
                            }
                        }
                    }else{
                        return new Object[]{false, "Bit'n are not activated."};
                    }
                }
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Caller is not a player."};
        }
    }

    @Callback(doc = "function(range:number):boolean, table; Search for entities in the range(1-128) and return a list of their UUIDs.", direct = true)
    public Object[] searchEntity(Context context, Arguments args){
        if(this.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.entity;
            if(args.checkInteger(0) >= 1 && args.checkInteger(0) <= 128){
                if(consumeEnergy(args.checkInteger(0))){
                    List<String>uuidList = new ArrayList<String>();
                    List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(
                        player.posX,
                        player.posY, 
                        player.posZ, 
                        player.posX, 
                        player.posY, 
                        player.posZ
                    ).grow(args.checkInteger(0)));
                    uuidList.add(player.getUniqueID().toString());
                    for(Entity entity : entities){
                        if(!(entity instanceof EntityBitn) && !entity.equals(this.entity) && entity instanceof EntityLivingBase){
                            uuidList.add(entity.getUniqueID().toString());
                        }
                    }
                    if(uuidList.size() > 0)return new Object[]{true, uuidList.toArray()};
                    else return new Object[]{false, "None of the target entities were found."};
                }
            }else{
                return new Object[]{false, "range must be between 1 and 128."};
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Caller is not a player."};
        }
    }

    @Callback(doc = "function(bitnId:number, uuid:string):boolean, string; Set a target for Bit'n.")
    public Object[] setTarget(Context context, Arguments args){
        if(this.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.entity;
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(args.checkInteger(0) > 0 && cbp.supportBitnList.size() >= args.checkInteger(0)){
                        EntityBitn bitn = cbp.supportBitnList.get(args.checkInteger(0) - 1);
                        if(bitn != null){
                            if(consumeEnergy()){
                                for(Entity entity:player.world.loadedEntityList){
                                    if(entity.getUniqueID().toString().equals(args.checkString(1))){
                                        bitn.target = entity;
                                        return new Object[]{true, entity.getDisplayName().getUnformattedText()};
                                    }
                                }
                                return new Object[]{false, "An entity with that UUID does not exist."};
                            }else{
                                return new Object[]{false, "Not enough energy."};
                            }
                        }else{
                            return new Object[]{false, "Bit'n of that Index does not exist."};
                        }
                    }else{
                        return new Object[]{false, "Bit'n of that Index does not exist."};
                    }
                }
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Caller is not a player."};
        }
    }

    @Callback(doc = "function(bitnId:number):boolean; Let Bit'n swing.", direct = true)
    public Object[] swing(Context context, Arguments args){
        if(this.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)this.entity;
            if(player.hasCapability(CapabilityBitnPlayer.BITN_PLAYER, null)){
                CapabilityBitnPlayer cbp = player.getCapability(CapabilityBitnPlayer.BITN_PLAYER, null);
                if(cbp != null){
                    if(args.checkInteger(0) > 0 && cbp.supportBitnList.size() >= args.checkInteger(0)){
                        EntityBitn bitn = cbp.supportBitnList.get(args.checkInteger(0) - 1);
                        if(bitn != null){
                            if(consumeEnergy()){
                                bitn.status = BitnConstructor.Status.ATTACK;
                                return new Object[]{true};
                            }else{
                                return new Object[]{false, "Not enough energy."};
                            }
                        }else{
                            return new Object[]{false, "Bit'n of that Index does not exist."};
                        }
                    }else{
                        return new Object[]{false, "Bit'n of that Index does not exist."};
                    }
                }
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Caller is not a player."};
        }
    }
}
