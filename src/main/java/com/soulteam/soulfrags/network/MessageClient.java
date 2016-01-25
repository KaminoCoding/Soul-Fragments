package com.soulteam.soulfrags.network;

import com.soulteam.soulfrags.SoulFragments;
import com.soulteam.soulfrags.playerdata.ExtendedPlayer;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageClient implements IMessage
{
	//Variable
	private boolean isMsgValid;
	private int soulAmount;
	private NBTTagCompound tag;
	
	public MessageClient() { }
	
	public MessageClient(EntityPlayer player)
	{
		ExtendedPlayer.get(player).saveNBTData(tag);
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
		tag = ByteBufUtils.readTag(buf); //read to nbt
	}
	
	//Called when the packet manager sends a packet
	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, tag); //write to nbt
		
	}
	
	public void process(EntityPlayer player)
	{
		ExtendedPlayer.get(player).loadNBTData(tag); //load?
	}
}
