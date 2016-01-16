package SoulFragments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

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
	public void load(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	// Called when a server is started, both solo and multiplayer
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{

	}
}
