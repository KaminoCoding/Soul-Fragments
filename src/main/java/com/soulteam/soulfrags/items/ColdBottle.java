package com.soulteam.soulfrags.items;

import com.soulteam.soulfrags.SoulFragments;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ColdBottle extends Item
{
	private final String itemName = "ColdBottle";
	public ColdBottle()
	{
		GameRegistry.registerItem(this, itemName);
		setUnlocalizedName(SoulFragments.MODID + "_" + itemName);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public String getName()
	{
		return itemName;
	}
}
