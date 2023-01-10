package com.moffy5612.soulstrike.integration.tconstruct.event;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.event.modelbake.IModelBakeEvents;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import slimeknights.tconstruct.shared.client.BakedTableModel;

public class ModelBakeCastingTable implements IModelBakeEvents{
    @Override
    public void runEvent(ModelBakeEvent event) {
        ResourceLocation reconstructedCasting = new ResourceLocation(Reference.MOD_ID, "reconstructed_casting_table");
        ModelResourceLocation reconstructedCastingTable = new ModelResourceLocation(reconstructedCasting, "type=table");
        IBakedModel model = event.getModelRegistry().getObject(reconstructedCastingTable);
        if(model != null){
            event.getModelRegistry().putObject(reconstructedCastingTable, new BakedTableModel(model, null, DefaultVertexFormats.BLOCK));
        }
    }
}
