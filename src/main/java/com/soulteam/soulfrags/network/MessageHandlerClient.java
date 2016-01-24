package com.soulteam.soulfrags.network;

import com.soulteam.soulfrags.SoulFragments;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerClient implements IMessageHandler<MessageClient, IMessage>
{
	//this is called when the message is received
	//thus, "Message Handler"
	@Override
	public IMessage onMessage(MessageClient message, MessageContext ctx)
	{
		//Error catchers :P
		if(ctx.side != Side.CLIENT)
		{
			System.err.println("The MessageClient was recieved on the wrong side: " + ctx.side);
			return null;
		}
		
		if(!message.messageValid())
		{
			System.err.println("The MessageClient was invalid: " + message.toString());
			return null;
		}
		
		//Now, we know that the side is the client side. We need to run the rest of the code
		//in a separate thread since 1.8 is... being a 1.8
		Minecraft minecraft = Minecraft.getMinecraft(); //get client minecraft
		final WorldClient worldClient = minecraft.theWorld; //get world
		minecraft.addScheduledTask(new Runnable() //run the task inside the run()
		{
			@Override
			public void run()
			{
				
			}
		});
		
		return null; //return null anyways, after doing stuff.
	}
}
