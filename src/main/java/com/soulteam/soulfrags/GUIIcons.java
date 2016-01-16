package com.soulteam.soulfrags;

import java.util.Collection;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class GUIIcons extends Gui
{
	private Minecraft mc;
	
	private static final int STATS_ICON_SIZE = 32;
	private static final int STATS_ICON_SPACING = STATS_ICON_SIZE + 10;
	private static final int STATS_ICON_BASE_U = 0;
	private static final int STATS_ICON_BASE_V = 0;
	private static final int STATS_ICONS_PER_ROW = 8;
	
	public GUIIcons(Minecraft mc)
	{
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent (priority = EventPriority.NORMAL)
	public void onRenderXPBar(RenderGameOverlayEvent event)
	{
		int xPos = 5;
		int yPos = 5;
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.mc.renderEngine.bindTexture(new ResourceLocation("soulfragments","textures/gui/icons.png"));
		this.drawTexturedModalRect(xPos, yPos, STATS_ICON_BASE_U, STATS_ICON_BASE_V, STATS_ICON_SIZE, STATS_ICON_SIZE);
	}
}
