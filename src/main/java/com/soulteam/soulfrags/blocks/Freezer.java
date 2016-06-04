package com.soulteam.soulfrags.blocks;

import com.soulteam.soulfrags.SoulFragments;
import com.soulteam.soulfrags.GUI.FreezerGUIHandler;
import com.soulteam.soulfrags.GUI.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Freezer extends BlockContainer
{
	//This is the actual block class for the freezer
	
	private final String name = "Freezer";
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public Freezer()
	{
		super(Material.rock);
		GameRegistry.registerBlock(this, name);
		GameRegistry.registerTileEntity(TileEntityFreezer.class, name);
		NetworkRegistry.INSTANCE.registerGuiHandler(SoulFragments.instance, GuiHandler.getInstance());
		GuiHandler.getInstance().registerGuiHandler(new FreezerGUIHandler(), FreezerGUIHandler.getGuiID());
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
		playerIn.openGui(SoulFragments.instance, FreezerGUIHandler.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
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

	public String getName()
	{
		return name;
	}
	
	//Everything below are for block rendering purposes
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{FACING});
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            Block block = worldIn.getBlockState(pos.north()).getBlock();
            Block block1 = worldIn.getBlockState(pos.south()).getBlock();
            Block block2 = worldIn.getBlockState(pos.west()).getBlock();
            Block block3 = worldIn.getBlockState(pos.east()).getBlock();
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFurnace)
            {
                ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }
	
    /** Convert the given metadata into a BlockState for this Block
    */
   public IBlockState getStateFromMeta(int meta)
   {
       EnumFacing enumfacing = EnumFacing.getFront(meta);

       if (enumfacing.getAxis() == EnumFacing.Axis.Y)
       {
           enumfacing = EnumFacing.NORTH;
       }

       return this.getDefaultState().withProperty(FACING, enumfacing);
   }

   /**
    * Convert the BlockState into the correct metadata value
    */
   public int getMetaFromState(IBlockState state)
   {
       return ((EnumFacing)state.getValue(FACING)).getIndex();
   }
    
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
}
