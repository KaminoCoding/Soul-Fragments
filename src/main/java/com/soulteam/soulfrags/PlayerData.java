package com.soulteam.soulfrags;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties
{
	public final static String PLYR_PROP_ID = ""; //unique entity tag ID
	private final EntityPlayer player;
	private int soulAmount, regenUsed; //two integers
	
	public PlayerData(EntityPlayer player)
	{
		this.player = player; //set value
		soulAmount = 100; //Everyone starts at 100 souls
		regenUsed = 0; //Everyone starts with 0 used regenerations
	}
	
	public static final PlayerData get(EntityPlayer player)
	{
		return (PlayerData) player.getExtendedProperties(PLYR_PROP_ID);
	}
	
	//register things
	public static final void _register (EntityPlayer player)
	{
		player.registerExtendedProperties(PLYR_PROP_ID, new PlayerData(player));
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger("soulAmount", soulAmount); //write both variables inside the tags
		properties.setInteger("regenUsed", regenUsed);
		compound.setTag(PLYR_PROP_ID, properties); //write to this tag [PLYR_PROP_ID]
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{   //read the NBT tag [PLYR_PROP_ID] from entity
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(PLYR_PROP_ID);
		this.soulAmount = properties.getInteger("soulAmount"); //read both values and store them inside the variables
		this.regenUsed = properties.getInteger("regenUsed");
	}

	@Override //IDK what this is used for :p
	public void init(Entity entity, World world) {}
	
	public void addSouls(int soulConstant)
	{
		this.soulAmount += soulConstant / this.soulAmount; //make sure that the player does not get infinite souls, exceeding 100 :p
	}
	
	public void useRegen()
	{
		if(this.regenUsed < 13)
		{
			this.regenUsed -= 1;
		}
		else if(this.regenUsed == 13)
		{
			this.regenUsed = 0; //reset counter
		}
		else
		{
			this.regenUsed = 0; //reset counter anyways
		}
	}
}
