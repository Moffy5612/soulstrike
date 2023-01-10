package com.moffy5612.soulstrike.integration.opencomputers.driver;

import com.moffy5612.soulstrike.integration.opencomputers.api.IPlayerListener;

import java.util.Iterator;

import com.moffy5612.soulstrike.integration.opencomputers.api.PlayerListeners;

import ben_mkiv.openentity.common.capability.authority.IauthorityCapability;
import ben_mkiv.openentity.common.capability.authority.authorityCapability;
import ben_mkiv.openentity.common.capability.energy.IenergyCapability;
import ben_mkiv.openentity.common.capability.energy.energyCapability;
import ben_mkiv.openentity.common.component.OpenEntityComputerComponent;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Node;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class DriverHiddenController extends OpenEntityComputerComponent implements IPlayerListener{
    public Entity target, connectedEntity;

    public DriverHiddenController(EnvironmentHost host){
        setupHost("hidden_controller", host);
        setupNode();
    }

    @Override
    public void onConnect(Node node) {
        // TODO Auto-generated method stub
        super.onConnect(node);
        if(node == this.node)PlayerListeners.listeners.add(this);
    }

    @Override
    public void onDisconnect(Node node) {
        // TODO Auto-generated method stub
        super.onDisconnect(node);
        if(node == this.node)PlayerListeners.listeners.remove(this);
    }

    @Override
    public void onEntityAttack(AttackEntityEvent event) {
        // TODO Auto-generated method stub
        this.target = event.getTarget();
        if(event.getEntity().equals(this.entity)){
            if(this.node() != null)this.node().sendToReachable("computer.signal", "player_attack", target.getDisplayName().getUnformattedText());
        }
    }

    @Override
    public void onEntityDamaged(LivingAttackEvent event) {
        // TODO Auto-generated method stub
        this.target = event.getSource().getTrueSource();
        if(event.getEntity().equals(this.entity)){
            if(this.node() != null)this.node().sendToReachable("computer.signal", "player_damaged", target.getDisplayName().getUnformattedText());
        }
    }

    @Override
    public void onEntityDeath(LivingDeathEvent event) {
        // TODO Auto-generated method stub
        this.target = event.getSource().getTrueSource();
        if(event.getEntity().equals(this.entity)){
            if(this.node() != null)this.node().sendToReachable("computer.signal", "player_death", target.getDisplayName().getUnformattedText());
        }
    }

    @Callback(doc = "function():table; Returns the entity's absolute position (x, y, z)", direct = true)
    public Object[] getAbsolutePosition(Context context, Arguments args){
            return new Object[]{this.entity.posX, this.entity.posY, this.entity.posZ};
    }

    @Callback(doc = "function():string; Returns the UUID of an entity.", direct = true)
    public Object[] getEntityUUID(Context context, Arguments args){
        return new Object[]{this.entity.getUniqueID().toString(), this.entity.getDisplayName().getUnformattedText()};
    }

    @Callback(doc = "function(uuid:string):boolean, string; Attempts to connect to an entity with the specified UUID.", direct = true)
    public Object[] connectEntity(Context context, Arguments args){
            Iterator<Entity> iterator = this.entity.world.getLoadedEntityList().iterator();
            while(iterator.hasNext()){
                Entity e = iterator.next();
                if(e.getUniqueID().toString().equals(args.checkString(0))){
                    if(e.hasCapability(authorityCapability.AUTHORITY, null)){
                        IauthorityCapability authority = e.getCapability(authorityCapability.AUTHORITY, null);
                        if(authority != null){
                            if(authority.getOwner().toString().equals(this.entity.getUniqueID().toString())){
                                this.connectedEntity = e;
                                return new Object[]{true, e.getDisplayName().getUnformattedText()};
                            }else{
                                return new Object[]{false, "You are not the owner of that entity."};
                            }
                        }
                    }else{
                        return new Object[]{false, "You need an authority interface to access that entity."};
                    }
                }
            }
            return new Object[]{false, "The entity with the that UUID does not exist."};
    }

    @Callback(doc = "function():boolean, string; ", direct = true)
    public Object[] shareEnergy(Context context, Arguments args){
        if(this.connectedEntity != null){
            if(this.entity.hasCapability(energyCapability.ENERGY, null) && this.connectedEntity.hasCapability(energyCapability.ENERGY, null)){
                IenergyCapability fromCapability = this.entity.getCapability(energyCapability.ENERGY, null);
                IenergyCapability toCapability = this.connectedEntity.getCapability(energyCapability.ENERGY, null);
                if(fromCapability != null && toCapability != null){
                    int transferRate = Math.min(fromCapability.getStorage().getTransferrate(), toCapability.getStorage().getTransferrate());
                    if(fromCapability.getStorage().extractEnergy(transferRate, true) >= 1){
                        int extracted = fromCapability.getStorage().extractEnergy(transferRate, false);
                        toCapability.getStorage().receiveEnergy(extracted, false);
                        return new Object[]{true, this.connectedEntity.getDisplayName().getUnformattedText()};
                    }else{
                        return new Object[]{false, "Not enough energy."};
                    }
                }
            }else{
                return new Object[]{false, "Energy Interface is required on both source and receiver."};
            }
            return new Object[]{false, "Unexpected error."};
        }else{
            return new Object[]{false, "Function 'connectEntity()' must be executed before using this function."};
        }
    }
}
