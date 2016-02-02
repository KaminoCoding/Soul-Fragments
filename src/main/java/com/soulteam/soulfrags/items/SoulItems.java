package com.soulteam.soulfrags.items;

import com.soulteam.soulfrags.blocks.*;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class SoulItems
{
	//items
	public static Item soulessence;
	public static Item soulshard;
	public static Item coldbottle;
	
	//foods
	public static ItemFood rawsoul;
	
	//blocks
	public static Block soullantern;
	
	public void registerItems(FMLInitializationEvent event, String MODID)
	{
		//items
		soulessence = new SoulEssence();
		soulshard = new SoulShard();
		coldbottle = new ColdBottle();
		
		//food
		rawsoul = new RawSoul();
		
		//blocks
		soullantern = new SoulLantern();
		
		if(event.getSide() == Side.CLIENT)
		{
			RenderItem rI = Minecraft.getMinecraft().getRenderItem();
			
			//items
			rI.getItemModelMesher().register(soulessence, 0, new ModelResourceLocation(MODID + ":" + ((SoulEssence) soulessence).getName(), "inventory"));
			rI.getItemModelMesher().register(soulshard, 0, new ModelResourceLocation(MODID + ":" + ((SoulShard) soulshard).getName(), "inventory"));
			rI.getItemModelMesher().register(coldbottle, 0, new ModelResourceLocation(MODID + ":" + ((ColdBottle) coldbottle).getName(), "inventory"));
			
			//food
			rI.getItemModelMesher().register(rawsoul, 0, new ModelResourceLocation(MODID + ":" + ((RawSoul) rawsoul).getName(), "inventory"));
			
			//blocks
			rI.getItemModelMesher().register(Item.getItemFromBlock(soullantern), 0, new ModelResourceLocation(MODID + ":" + ((SoulLantern) soullantern).getName(), "inventory"));
		}
	}
	
	public void registerRecipes()
	{
		GameRegistry.addShapedRecipe(new ItemStack(soulshard, 1), "XX", "XX", 'X', soulessence);
		GameRegistry.addShapedRecipe(new ItemStack(soullantern, 1), "XX", "XX", 'X', soulshard);
		GameRegistry.addShapelessRecipe(new ItemStack(rawsoul, 1), new ItemStack(coldbottle, 1), soulessence);
	}
}
