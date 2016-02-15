package com.soulteam.soulfrags.blocks;

import com.soulteam.soulfrags.SoulFragments;
import com.soulteam.soulfrags.GUI.GuiHandler;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Freezer extends BlockContainer
{
	//This is the actual block class for the freezer
	
	private final String name = "Freezer";
	
	protected Freezer()
	{
		super(Material.rock);
		GameRegistry.registerBlock(this, name);
		setUnlocalizedName(SoulFragments.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabMisc);
		setLightOpacity(5);
	}
	
	//Called whenever the block is placed or loaded client side in order to
	//get the tile entity associated with the block
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFreezer();
	}
	
	//Called whenever the block is used (i.e., right click)
	//In this block, the gui is opened
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote)
			return true;
		playerIn.openGui(SoulFragments.instance, GuiHandler.getGuiID(), worldIn, pos.getX(), pos.getX(), pos.getZ());
		return true;
	}
	
	//This is called when the block is broken
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		//Drop items
		TileEntity tEntity = worldIn.getTileEntity(pos);
		if(tEntity instanceof IInventory)
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tEntity);
		super.breakBlock(worldIn, pos, state); //removes the block
	}
	
	//Everything below is for block rendering purposes
}
