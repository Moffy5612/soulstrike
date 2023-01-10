package com.moffy5612.soulstrike.integration.tconstruct;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.SoulStrike;
import com.moffy5612.soulstrike.block.Blocks;
import com.moffy5612.soulstrike.event.modelbake.ModelBakeEvents;
import com.moffy5612.soulstrike.integration.tconstruct.block.BlockReconstructedCastingTable;
import com.moffy5612.soulstrike.integration.tconstruct.block.BlockReconstructedForge;
import com.moffy5612.soulstrike.integration.tconstruct.block.BlockReconstructedPartBuilder;
import com.moffy5612.soulstrike.integration.tconstruct.conarm.ArmorModifierSectionTransformer;
import com.moffy5612.soulstrike.integration.tconstruct.event.ModelBakeCastingTable;
import com.moffy5612.soulstrike.integration.tconstruct.event.SoulAroundEvent;
import com.moffy5612.soulstrike.integration.tconstruct.gui.TConstructGuiHandler;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedCastingTable;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedForge;
import com.moffy5612.soulstrike.integration.tconstruct.tileentity.TileReconstructedPartBuilder;
import com.moffy5612.soulstrike.integration.tconstruct.trait.Traits;
import com.moffy5612.soulstrike.item.Items;
import com.moffy5612.soulstrike.utils.ItemUtil;

import c4.conarm.lib.book.ArmoryBook;
import c4.conarm.lib.utils.RecipeMatchHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TConstructIntegration {
    public static void preInit(){
        Blocks.RECONSTRUCTED_CASTING_TABLE = new BlockReconstructedCastingTable();
        Blocks.RECONSTRUCTED_PART_BUILDER = new BlockReconstructedPartBuilder();
        Blocks.RECONSTRUCTED_FORGE = new BlockReconstructedForge();

        Items.regBlockItems.add(ItemUtil.getItemInBlock(Blocks.RECONSTRUCTED_CASTING_TABLE).setRegistryName(Reference.MOD_ID, BlockReconstructedCastingTable.NAME));
        Items.regBlockItems.add(ItemUtil.getItemInBlock(Blocks.RECONSTRUCTED_PART_BUILDER).setRegistryName(Reference.MOD_ID, BlockReconstructedPartBuilder.NAME));
        Items.regBlockItems.add(ItemUtil.getItemInBlock(Blocks.RECONSTRUCTED_FORGE).setRegistryName(Reference.MOD_ID, BlockReconstructedForge.NAME));

        Blocks.regBlocks.add(Blocks.RECONSTRUCTED_CASTING_TABLE);
        Blocks.regBlocks.add(Blocks.RECONSTRUCTED_PART_BUILDER);
        Blocks.regBlocks.add(Blocks.RECONSTRUCTED_FORGE);

        if(Loader.isModLoaded("conarm")){
            MinecraftForge.EVENT_BUS.register(new SoulAroundEvent());

            Traits.regArmorModifiers.add(Traits.SOUL_AROUND);

            RecipeMatchHolder.addItem(Traits.SOUL_AROUND, Items.PRIDE_OF_ROUND);
        }
        GameRegistry.registerTileEntity(TileReconstructedCastingTable.class, new ResourceLocation(Reference.MOD_ID, "tile.reconstructed_casting_table"));
        GameRegistry.registerTileEntity(TileReconstructedPartBuilder.class, new ResourceLocation(Reference.MOD_ID, "tile.reconstructed_part_builder"));
        GameRegistry.registerTileEntity(TileReconstructedForge.class, new ResourceLocation(Reference.MOD_ID, "tile.reconstructed_forge"));
        if(SoulStrike.proxy.isClient())preInitClient();
    }

    public static void init(){
        NetworkRegistry.INSTANCE.registerGuiHandler(SoulStrike.instance, new TConstructGuiHandler());
    }

    public static void postInit(){
        if(Loader.isModLoaded("conarm")){
            ArmoryBook.INSTANCE.addTransformer(new ArmorModifierSectionTransformer());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient(){
        ModelBakeEvents.TABLE_MODEL_BAKE = new ModelBakeCastingTable();
        ModelBakeEvents.modelBakeEvents.add(ModelBakeEvents.TABLE_MODEL_BAKE);
    }
}
