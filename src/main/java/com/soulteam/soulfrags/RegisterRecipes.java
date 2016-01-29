package com.soulteam.soulfrags;

import com.soulteam.soulfrags.items.SoulEssence;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterRecipes
{
	public void registerRecipes()
	{
		ItemStack wESword = new ItemStack(Items.wooden_sword);
		wESword.addEnchantment(Enchantment.sharpness, 3);
		GameRegistry.addShapelessRecipe(wESword, Items.flint, Items.wooden_sword);

		ItemStack sESword = new ItemStack(Items.stone_sword);
		sESword.addEnchantment(Enchantment.sharpness, 2);
		GameRegistry.addShapelessRecipe(sESword, Items.flint, Items.stone_sword);

		ItemStack iESword = new ItemStack(Items.iron_sword);
		iESword.addEnchantment(Enchantment.sharpness, 1);
		GameRegistry.addShapelessRecipe(iESword, Items.flint, Items.iron_sword);
	}
}
