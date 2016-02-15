package com.soulteam.soulfrags.GUI;

import com.soulteam.soulfrags.blocks.TileEntityFreezer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class FreezerGUI extends GuiContainer
{

	public FreezerGUI(InventoryPlayer invPlayer, TileEntityFreezer tileFreezer)
	{
		super(new FreezerGUIInventory(invPlayer, tileFreezer));
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		
	}

}
