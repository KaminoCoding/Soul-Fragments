package com.soulteam.soulfrags.GUI;

import com.soulteam.soulfrags.blocks.TileEntityFreezer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class FreezerGUI extends GuiContainer
{
	//Resource location for the gui texture
	private static final ResourceLocation texture = new ResourceLocation("soulfragments", "textures/gui/freezer_gui.png");
	private TileEntityFreezer tileEntity;
	
	public FreezerGUI(InventoryPlayer invPlayer, TileEntityFreezer tileFreezer)
	{
		super(new FreezerGUIInventory(invPlayer, tileFreezer));
		xSize = 175; //set xSize, ySize
		ySize = 165; //Values retrieved from GIMP :)
		this.tileEntity = tileFreezer;
	}
	
	final int FREEZE_FLAME_X = 27;
	final int FREEZE_FLAME_Y = 43;
	final int FREEZE_ICON_U = 176;
	final int FREEZE_ICON_V = 15;
	
	final int FLAME_X = 81;
	final int FLAME_Y = 25;
	final int FLAME_ICON_U = 176;
	final int FLAME_ICON_V = 0;
	
	final int COOK_BAR_X = 50;
	final int COOK_BAR_Y = 40;
	final int COOK_BAR_ICON_U = 0;
	final int COOK_BAR_ICON_V = 166;
	final int COOK_BAR_WIDTH = 83; //Add one
	final int COOK_BAR_HEIGHT = 16;
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture); //Bind texture
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); //Draw texture
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		//Get freeze progress as a double between 0 and 1
		double freezeProgress = tileEntity.fractionFreezeTimeComplete();
		double freezeRemaining = tileEntity.fractionFuelRemaining(true);
		double fuelRemaining = tileEntity.fractionFuelRemaining(false);
		
		int yOffset1 = (int)((1.0 - fuelRemaining) * 14);
		int yOffset2 = (int)((1.0 - freezeRemaining) * 14);
		
		//Draw the progress bar, the freeze icon and the fuel fire icon
		drawTexturedModalRect(guiLeft + COOK_BAR_X, guiTop + COOK_BAR_Y, COOK_BAR_ICON_U, COOK_BAR_ICON_V, (int)(freezeProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);
		
		drawTexturedModalRect(guiLeft + FREEZE_FLAME_X, guiTop + FREEZE_FLAME_Y + yOffset2, FREEZE_ICON_U, FREEZE_ICON_V + yOffset2, 14, 14 - yOffset2);
		drawTexturedModalRect(guiLeft + FLAME_X, guiTop + FLAME_Y + yOffset1, FLAME_ICON_U, FLAME_ICON_V + yOffset1, 14, 14 - yOffset1);
	}

}
