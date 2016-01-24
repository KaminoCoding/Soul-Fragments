package com.soulteam.soulfrags.playerdata;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties
{
	public final static String EXT_ID = "Soulfrags_S"; //The ID of the unique name
	private final EntityPlayer player; //entity player
	private int soulAmount, regen; //values to store
	
	public ExtendedPlayer(EntityPlayer player)
	{
		this.player = player; //Initialize the player variable
		this.soulAmount = 100; //Add values to the ints
		this.regen = 13;
	}
	
	//register the extended prop.s for the player
	public static final void register(EntityPlayer player)
	{
		//Added method for sake of simplicity
		player.registerExtendedProperties(EXT_ID, new ExtendedPlayer(player));
	}
	
	//returns the properties for the player
	public static final ExtendedPlayer get (EntityPlayer player)
	{
		return (ExtendedPlayer)
		player.getExtendedProperties(EXT_ID);
	}
	
	//save method. self-explanatory
	@Override //these are implemented methods
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound tag = new NBTTagCompound(); //create new tag compound to save the variables for the extended props
		tag.setInteger("SoulAmount", soulAmount); //set value soulamount
		//TODO: Add set value for "regen" later
		compound.setTag(EXT_ID, tag); //set the tag with the EXT_ID and the values inside "tag"
	}
	
	//load method
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		//same thing as above (almost)
		//Fetch the tag compound with the unique ID
		NBTTagCompound tag = (NBTTagCompound) compound.getTag(EXT_ID); //get the current tag witht eh EXT_ID (the unique id)
		this.soulAmount = tag.getInteger("SoulAmount"); //assign value
	}
	
	//init :)
	@Override
	public void init(Entity entity, World world)
	{
		//what is this used for?
	}

}
