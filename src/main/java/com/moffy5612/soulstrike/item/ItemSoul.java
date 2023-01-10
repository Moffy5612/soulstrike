package com.moffy5612.soulstrike.item;

import java.util.List;

import javax.annotation.Nullable;

import com.moffy5612.soulstrike.SoulStrike;
import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.utils.Element;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

public class ItemSoul extends Item{

    private int element;

    public ItemSoul(String name, int element){
        super();

        this.element = element;

        this.setRegistryName(Reference.MOD_ID, name);
        this.setUnlocalizedName(Reference.MOD_ID+":"+name);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        // TODO Auto-generated method stub
        return new SoulRarity();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(SoulStrike.proxy.translate("item."+this.getRegistryName()+".desc", Element.getColorStyle(this.element)));
        tooltip.add("");
        SoulStrike.proxy.addMultiLineDescription(
            tooltip, 
            "item."+this.getRegistryName()+".phrase", 
            new Style().setColor(TextFormatting.GRAY).setItalic(true)
        );
    }

    public class SoulRarity implements IRarity{

        @Override
        public TextFormatting getColor() {
            // TODO Auto-generated method stub
            return EnumRarity.EPIC.rarityColor;
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return EnumRarity.EPIC.rarityName;
        }
        
    }
}
