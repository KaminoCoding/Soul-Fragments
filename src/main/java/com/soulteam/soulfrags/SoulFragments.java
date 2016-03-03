package com.soulteam.soulfrags;

import api.player.client.ClientPlayerAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soulteam.soulfrags.GUI.GUIIcons;
import com.soulteam.soulfrags.items.*;
import com.soulteam.soulfrags.playerdata.ClientPlayerApiBase;
import com.soulteam.soulfrags.playerdata.PlayerEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = SoulFragments.MODID, name = SoulFragments.MODNAME, version = SoulFragments.MODVER, useMetadata = true)
public class SoulFragments
{
	//public static SimpleNetworkWrapper net_wrapper; packet use is now disabled
	
	public static final String MODID = "soulfrags";
	public static final String MODNAME = "Soul Fragments";
	public static final String MODVER = "0.0.1";
	public static final Logger Logger = LogManager.getLogger(MODID);
	private SoulItems regI = new SoulItems();
	
	public SoulFragments()
	{
		ClientPlayerAPI.register(MODID, ClientPlayerApiBase.class);
	}

	@Instance(value = SoulFragments.MODID)
	public static SoulFragments instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		regI.registerTiles(event, MODID);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//Register Events
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		
		//register items
		regI.registerItems(event, MODID);
		
		//register recipes
		regI.registerRecipes();
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
