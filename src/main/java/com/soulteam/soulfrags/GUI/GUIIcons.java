package com.soulteam.soulfrags.GUI;

import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.soulteam.soulfrags.playerdata.ExtendedPlayer;

@SideOnly(Side.CLIENT)
public class GUIIcons extends Gui
{
	private Minecraft mc;
	
	//Variables: These variables are for the 256x256 tiled texture
	private static final int STATS_ICON_SIZE = 32; //Icon size
	private static final int STATS_ICON_SPACING = STATS_ICON_SIZE + 10; //Space between icons when displayed
	private static final int STATS_ICON_BASE_U = 0; //Location offset of icon (x) in pixels
	private static final int STATS_ICON_BASE_V = 0; //Location offset of icon (y) in pixels
	private static final int STATS_ICONS_PER_ROW = 8; //Icons per row
	
	//Key Bindings
	public static KeyBinding gui_open;
	
	//Initiate things
	public GUIIcons(Minecraft mc)
	{
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent (priority = EventPriority.NORMAL)
	//Draw stuff during XP render
	public void onRenderXPBar(RenderGameOverlayEvent.Post event)
	{
		//EntityPlayer player = mc.thePlayer;
		ExtendedPlayer extPlyr = ExtendedPlayer.get(this.mc.thePlayer);
		if(event.isCancelable()||event.type != ElementType.EXPERIENCE)
			return;
		int scale = mc.gameSettings.guiScale + 1; //Get the scale settings, 1 = Smallest, 4 = Largest, 2 to 3 = everything else
		int xPos = 5; //X, Y coordinates on screen
		int yPos = 255 / scale; //Make sure the label don't go off screen when on different GUI scales :p
		GL11.glColor4f(1F, 1F, 1F, 1F); //Drawing settings stuff
		GL11.glDisable(GL11.GL_LIGHTING);
		//Texture location
		this.mc.renderEngine.bindTexture(new ResourceLocation("soulfragments","textures/gui/gui.png"));
		
		//Draw the texture.
		this.drawTexturedModalRect(xPos, yPos, STATS_ICON_BASE_U, STATS_ICON_BASE_V, STATS_ICON_SIZE, STATS_ICON_SIZE);
		this.drawTexturedModalRect(xPos, yPos + STATS_ICON_SIZE + 5, STATS_ICON_BASE_U + 32, STATS_ICON_BASE_V, STATS_ICON_SIZE, STATS_ICON_SIZE);
		String soul = extPlyr.getSoul() + "";
		this.mc.fontRendererObj.drawString(soul, xPos + 28, yPos + 28, 0xFFFFFF); //regeneration energy
		//this.mc.fontRendererObj.drawString("0", xPos + 28, yPos + STATS_ICON_SIZE + 33, 0xFFFFFF);
		this.mc.fontRendererObj.drawString("0", xPos + 28, yPos + STATS_ICON_SIZE + 33, 0xFFFFFF); //soul
	}
}
