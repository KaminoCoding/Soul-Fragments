package com.soulteam.soulfrags;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		//Check if the entity is a player
		if(event.entity instanceof EntityPlayer && PlayerData.get((EntityPlayer)event.entity) == null)
		{
			PlayerData._register((EntityPlayer)event.entity); //register
		}
		
		if(event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(PlayerData.PLYR_PROP_ID) == null)
		{
			event.entity.registerExtendedProperties(PlayerData.PLYR_PROP_ID, new PlayerData((EntityPlayer)event.entity));
		}
	}
}
