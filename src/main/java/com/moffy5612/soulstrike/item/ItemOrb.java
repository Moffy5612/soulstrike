package com.moffy5612.soulstrike.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.moffy5612.soulstrike.Reference;
import com.moffy5612.soulstrike.SoulStrike;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

public class ItemOrb extends Item{
    public Random random;
    public List<ItemStack> gachaResults;

    public boolean isMasterOrb;
    public static final String ORB_NAME = "orb";
    public static final String MASTER_ORB_NAME = "master_orb";

    public ItemOrb(boolean isMasterOrb){
        super();
        this.setRegistryName(Reference.MOD_ID, isMasterOrb ? MASTER_ORB_NAME : ORB_NAME);
        this.setUnlocalizedName(Reference.MOD_ID + ":" + (isMasterOrb ? MASTER_ORB_NAME : ORB_NAME));
        this.random = new Random();
        this.gachaResults = new ArrayList<ItemStack>();
        this.isMasterOrb = isMasterOrb;
        if(!isMasterOrb)gachaResultsInit();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        // TODO Auto-generated method stub
        SoulStrike.proxy.addMultiLineDescription(
            tooltip, 
            "item."+this.getRegistryName()+".desc", 
            new Style().setColor(TextFormatting.GRAY)
        );
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        // TODO Auto-generated method stub
        return new OrbRarity(this.isMasterOrb ? EnumRarity.RARE : EnumRarity.UNCOMMON);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        // TODO Auto-generated method stub
        return this.isMasterOrb;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!this.isMasterOrb){
            if(!playerIn.capabilities.isCreativeMode){
                stack.shrink(1);
            }

            if(!worldIn.isRemote){
                if(random.nextInt(10) < 4){
                    playerIn.dropItem(Items.regSoulItems.get(random.nextInt(Items.regSoulItems.size())), 1);
                }else{
                    playerIn.dropItem(gachaResults.get(random.nextInt(gachaResults.size())), false, true);
                }
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }

    public void gachaResultsInit(){
        gachaResults.add(new ItemStack(Item.getByNameOrId("minecraft:diamond")));
        gachaResults.add(new ItemStack(Item.getByNameOrId("minecraft:redstone")));
        gachaResults.add(new ItemStack(Item.getByNameOrId("minecraft:glowstone_dust")));
        gachaResults.add(new ItemStack(Item.getByNameOrId("minecraft:dye"), 1, 4));
        
    }

    public class OrbRarity implements IRarity{

        public TextFormatting color;
        public String name;

        public OrbRarity(EnumRarity rarity){
            this.name = rarity.getName();
            this.color = rarity.getColor();
        }

        @Override
        public TextFormatting getColor() {
            // TODO Auto-generated method stub
            return this.color;
        }

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return this.name;
        }
        
    }
}
