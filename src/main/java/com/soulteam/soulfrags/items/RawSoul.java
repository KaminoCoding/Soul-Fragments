package com.soulteam.soulfrags.items;

import com.soulteam.soulfrags.SoulFragments;
import com.soulteam.soulfrags.playerdata.ExtendedPlayer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RawSoul extends ItemFood
{
	private final String itemName = "RawSoul";
	
	public RawSoul()
	{
		super(5, 1.0f, false); //heal amount, saturation, isWolfFood
		GameRegistry.registerItem(this, itemName);
		setUnlocalizedName(SoulFragments.MODID + "_" + itemName);
		setCreativeTab(CreativeTabs.tabMisc);
		setAlwaysEdible();
		setMaxStackSize(1);
		
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        super.onItemUseFinish(stack, worldIn, playerIn);
        ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) playerIn);
        props.addSoul(10, false);
        return new ItemStack(SoulItems.coldbottle);
    }
	
	public String getName()
	{
		return itemName;
	}
}
