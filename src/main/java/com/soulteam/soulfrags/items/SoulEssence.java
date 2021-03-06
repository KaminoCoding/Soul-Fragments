package com.soulteam.soulfrags.items;

import com.soulteam.soulfrags.SoulFragments;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SoulEssence extends Item
{
	private final String itemName = "SoulEssence";
	public SoulEssence()
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
