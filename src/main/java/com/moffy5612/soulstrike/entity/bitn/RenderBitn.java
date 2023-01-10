package com.moffy5612.soulstrike.entity.bitn;

import javax.annotation.Nullable;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBitn extends RenderLiving<EntityLiving>{
    public final ResourceLocation TEXTURE_BITN = new ResourceLocation("soulstrike:textures/entities/bitn.png");
    public RenderBitn(RenderManager manager, ModelBase base, float shadowSize){
        super(manager, base, shadowSize);
        this.addLayer(new LayerBitnHeldItem(this));
    }

    @Override
    @Nullable
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return this.TEXTURE_BITN;
    }
}
