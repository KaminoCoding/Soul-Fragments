package com.soulteam.soulfrags.playerdata;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler
{
	//event handler class :)
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		//Check if the entity being constructed is the correct type for the extended properties
		//Also make sure the properties are only registered once per entity
		//The checks are to make sure the entity type is a player, not a cow or pig or etc.
		if(event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null)
		{
			//use that method created in the ExtendedPlayer class to register the thing
			ExtendedPlayer.register((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onLivingFall(LivingFallEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entity);
			if(event.distance >= 3.0f)
				props.addSoul(10, true);
		}
	}
}
