package com.soulteam.soulfrags.items;

import com.soulteam.soulfrags.blocks.*;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class RegisterItems
{
	public Item soulessence;
	public Item soulshard;
	
	public Block soullantern;
	
	public void registerItems(FMLInitializationEvent event, String MODID)
	{
		soulessence = new SoulEssence();
		soulshard = new SoulShard();
		
		soullantern = new SoulLantern();
		
		if(event.getSide() == Side.CLIENT)
		{
			RenderItem rI = Minecraft.getMinecraft().getRenderItem();
			rI.getItemModelMesher().register(soulessence, 0, new ModelResourceLocation(MODID + ":" + ((SoulEssence) soulessence).getName(), "inventory"));
			rI.getItemModelMesher().register(soulshard, 0, new ModelResourceLocation(MODID + ":" + ((SoulShard) soulshard).getName(), "inventory"));
			
			rI.getItemModelMesher().register(Item.getItemFromBlock(soullantern), 0, new ModelResourceLocation(MODID + ":" + ((SoulLantern) soullantern).getName(), "inventory"));
		}
	}
	
	public void registerRecipes()
	{
		GameRegistry.addShapedRecipe(new ItemStack(soulshard, 1), "XX", "XX", 'X', soulessence);
		GameRegistry.addShapedRecipe(new ItemStack(soullantern, 1), "XX", "XX", 'X', soulshard);
	}
}
