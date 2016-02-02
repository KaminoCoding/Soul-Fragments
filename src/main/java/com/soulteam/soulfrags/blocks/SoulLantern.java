package com.soulteam.soulfrags.blocks;

import com.soulteam.soulfrags.SoulFragments;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoulLantern extends Block
{
	private final String name = "SoulLantern";
	public SoulLantern()
	{
		super(Material.glass);
		GameRegistry.registerBlock(this, name);
		setUnlocalizedName(SoulFragments.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabMisc);
		setLightLevel(1.0F);
		setResistance(50.3F);
		setHardness(3.6F);
		setHarvestLevel("pickaxe", 2);
	}
	
	public String getName()
	{
		return name;
	}
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
