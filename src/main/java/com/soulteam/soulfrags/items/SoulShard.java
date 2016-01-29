package com.soulteam.soulfrags.items;

import com.soulteam.soulfrags.SoulFragments;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SoulShard extends Item
{
	private final String itemName = "SoulShard";
	public SoulShard()
	{
		GameRegistry.registerItem(this, itemName);
		setUnlocalizedName(SoulFragments.MODID + "_" + itemName);
		//setTextureName(SoulFragments.MODID + ":" + itemName);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public String getName()
	{
		return itemName;
	}
}
