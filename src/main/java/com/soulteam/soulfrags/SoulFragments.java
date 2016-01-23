package com.soulteam.soulfrags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = SoulFragments.MODID, name = SoulFragments.MODNAME, version = SoulFragments.MODVER)
public class SoulFragments
{
	public static final String MODID = "SoulFragments";
	public static final String MODNAME = "Soul Fragments";
	public static final String MODVER = "0.0.1";
	public static final Logger Logger = LogManager.getLogger(MODID);

	public SoulFragments()
	{

	}

	@Instance(value = SoulFragments.MODID)
	public static SoulFragments instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{

	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//Register Events
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		
		//I'll clean up the code l8tr. 4 now, just throw in recipes
		//Sharpen swords XD I just like the idea of it. Nice easter egg to throw into the mod
		ItemStack wESword = new ItemStack(Items.wooden_sword);
		wESword.addEnchantment(Enchantment.sharpness, 3);
		GameRegistry.addShapelessRecipe(wESword, Items.flint, Items.wooden_sword);
		
		ItemStack sESword = new ItemStack(Items.stone_sword);
		wESword.addEnchantment(Enchantment.sharpness, 2);
		GameRegistry.addShapelessRecipe(wESword, Items.flint, Items.stone_sword);
		
		ItemStack iESword = new ItemStack(Items.iron_sword);
		wESword.addEnchantment(Enchantment.sharpness, 1);
		GameRegistry.addShapelessRecipe(wESword, Items.flint, Items.iron_sword);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		//GUI overlay for soul status
		MinecraftForge.EVENT_BUS.register(new GUIIcons(Minecraft.getMinecraft()));
	}

	// Called when a server is started, both solo and multiplayer
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		
	}
}
