package com.soulteam.soulfrags.items;

import java.util.Iterator;
import java.util.List;

import com.soulteam.soulfrags.CustomDrink;
import com.soulteam.soulfrags.SoulFragments;
import com.soulteam.soulfrags.playerdata.ExtendedPlayer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RawSoul extends CustomDrink
{
	private final String itemName = "RawSoul";
	
	public RawSoul()
	{
		super(null, SoulItems.coldbottle); //heal amount, saturation, isWolfFood
		GameRegistry.registerItem(this, itemName);
		setUnlocalizedName(SoulFragments.MODID + "_" + itemName);
		setCreativeTab(CreativeTabs.tabMisc);
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
