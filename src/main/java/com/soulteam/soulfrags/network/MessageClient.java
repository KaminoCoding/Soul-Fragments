package com.soulteam.soulfrags.network;

import com.soulteam.soulfrags.SoulFragments;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageClient implements IMessage
{
	//Variable
	private boolean isMsgValid;
	private int soulAmount;
	
	public MessageClient()
	{
		isMsgValid = true; //called correct thing
	}
	
	//Check if the message is valid. Not necessary, but acts as a fail-safe
	public boolean messageValid()
	{
		return isMsgValid;
	}
	
	//Called when the packet manager receives a packet.
	@Override
	public void fromBytes(ByteBuf buf)
	{
		//TODO: Add NBT tag class
	}
	
	//Called when the packet manager sends a packet
	@Override
	public void toBytes(ByteBuf buf)
	{
		//TODO: Add NBT class
		
	}
}
