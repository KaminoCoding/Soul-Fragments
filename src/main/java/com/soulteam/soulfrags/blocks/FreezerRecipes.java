package com.soulteam.soulfrags.blocks;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.soulteam.soulfrags.items.SoulItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FreezerRecipes
{
	//This class is heavily based on FurnaceRecipes.class from vanilla minecraft
	
	//A hashmap of all the freezer recipes
	private Map freezeList = Maps.newHashMap();
	private static final FreezerRecipes freezerBase = new FreezerRecipes();
	
	//Returns an instance of this
	public static FreezerRecipes instance()
	{
		return freezerBase;
	}
	
	private FreezerRecipes()
	{
		//Initialize recipes here
		this.addRecipe(Items.glass_bottle, new ItemStack(SoulItems.coldbottle));
		
	}
	
	public void addRecipeForBlock(Block input, ItemStack output)
	{
		this.addFreezerRecipes(new ItemStack(Item.getItemFromBlock(input)), output);
	}
	
	public void addRecipe(Item input, ItemStack output)
	{
		this.addFreezerRecipes(new ItemStack(input), output);
	}
	
	public void addFreezerRecipes(ItemStack input, ItemStack output)
	{
		if(recipeReturnStack(input) != null)
		{
			net.minecraftforge.fml.common.FMLLog.info("Ignored freezer recipe with conflicting input: " + input + " = " + output);
			return;
		}
		this.freezeList.put(input, output);
	}
	
	public ItemStack recipeReturnStack(ItemStack input)
	{
		Iterator iterator = this.freezeList.entrySet().iterator();
		Entry entry;
		do
		{
			if(!iterator.hasNext())
				return null;
			entry = (Entry)iterator.next();
		}
		while(!compareStacks(input, (ItemStack)entry.getKey()));
		return (ItemStack)entry.getValue();
	}
	
	private boolean compareStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Map getFreezerList()
	{
		return freezeList;
	}
}
