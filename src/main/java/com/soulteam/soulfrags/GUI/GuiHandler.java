package com.soulteam.soulfrags.GUI;

import com.soulteam.soulfrags.blocks.*;
import com.soulteam.soulfrags.GUI.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	//This can only be registered once per mod
	private static final int GuiID = 42; //GUI id
	public static int getGuiID(){ return GuiID; }
	
	//Gets server side gui element from ID
	//returns a GUI Container
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID != GuiID)
			System.err.println("Invalid GUIID recieved. Expected " + GuiID + " but recieved " + ID);
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
		if(tileentity instanceof TileEntityFreezer)
		{
			TileEntityFreezer tilefreezer = (TileEntityFreezer) tileentity;
			return new FreezerGUIInventory(player.inventory, tilefreezer);
		}
			
		return null;
	}
	
	//Gets the client side gui element from ID
	//Returns a gui
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID != GuiID)
			System.err.println("Invalid GUIID recieved. Expected " + GuiID + " but recieved " + ID);
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));
		if(tileentity instanceof TileEntityFreezer)
		{
			TileEntityFreezer tilefreezer = (TileEntityFreezer) tileentity;
			return new FreezerGUI(player.inventory, tilefreezer);
		}
			
		return null;
	}
}
