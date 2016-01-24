package com.soulteam.soulfrags.items;

import com.soulteam.soulfrags.SoulFragments;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SoulEssence extends Item
{
	public SoulEssence(String itemName)
	{
		setUnlocalizedName(SoulFragments.MODID + "_" + itemName);
		//setTextureName(SoulFragments.MODID + ":" + itemName);
		setCreativeTab(CreativeTabs.tabMisc);
	}
}
