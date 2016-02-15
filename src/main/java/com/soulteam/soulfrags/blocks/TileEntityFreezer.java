package com.soulteam.soulfrags.blocks;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;
import com.soulteam.soulfrags.GUI.FreezerGUIInventory;
import com.soulteam.soulfrags.GUI.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import scala.Array;

public class TileEntityFreezer extends TileEntityLockable implements IUpdatePlayerListBox, ISidedInventory
{
	//---------------------------------------------------------------//
	//----------------------VARIABLES--------------------------------//
	//public enum SlotEnum{INPUT_SLOT, OUTPUT_SLOT, FREEZER_SLOT, FUEL_SLOT};
	
	public static final int TOTAL_SLOTS = 4; //how many slots there are in the container
	private ItemStack[] itemstacks = new ItemStack[TOTAL_SLOTS]; //inventory array. each array slot contains an itemstack
	public static final int INPUT_SLOT = 0;
	public static final int FUEL_SLOT = 1;
	public static final int FREEZER_SLOT = 2;
	public static final int OUTPUT_SLOT = 3;
	
	private static int SLOTS_BOTTOM[] = new int[] {3};
	private static int SLOTS_SIDE[] = new int[] {1, 2};
	private static int SLOTS_TOP[] = new int[] {0};
	
	//private static final short COOK_TIME_COMPLETION = 200; //20 ticks per second. 200 ticks = 10 seconds
	private int freezeTime = 0; //Current freeze time completed
	private int COOK_TIME_COMPLETION = 200; //Freeze rate of current item
	private int currentFuelBurnTime = 0; //How much time a fresh piece of [the] fuel will burn for
	private int currentFuelFreezeTime = 0; //How much fast a fresh peice of [the] coolent will cool
	//---------------------------------------------------------------//
	
	public boolean freezingSomething() { return freezeTime > 0; }
	
	public ItemStack getFreezeRecipe(ItemStack input){ return FreezerRecipes.instance().recipeReturnStack(input); }
	
	public static int getFuelFreezeTime(ItemStack fuel)
	{
		if(fuel == null)
			return 0;
		else
		{
			Item item = fuel.getItem();
			if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
			{
				Block block = Block.getBlockFromItem(item);
				if(block == Blocks.ice)
					return 360;
				if(block == Blocks.packed_ice)
					return 560;
			}
			if(item == Items.water_bucket)
				return 200;
			return 100;
		}
	}
	
	public static int getFuelTime(ItemStack fuel) { return TileEntityFurnace.getItemBurnTime(fuel); }
	
	public boolean isItemFuel(ItemStack input)
	{
		return getFuelFreezeTime(input) > 0 || getFuelTime(input) > 0;
	}
	
	// returns the number of ticks the given item will burn. Returns 0 if the given item is not a valid fuel
	public static short getItemBurnTime(ItemStack stack)
	{
		int burntime = TileEntityFurnace.getItemBurnTime(stack);  // just use the vanilla values
		return (short)MathHelper.clamp_int(burntime, 0, Short.MAX_VALUE);
	}
	
	public boolean freezeObject(boolean doSmelt)
	{
		Integer outputSlot = null;
		ItemStack output = null;
		if(itemstacks[INPUT_SLOT] != null)
		{
			output = getFreezeRecipe(itemstacks[INPUT_SLOT]);
			if(output == itemstacks[OUTPUT_SLOT] || itemstacks[OUTPUT_SLOT] == null)
				outputSlot = OUTPUT_SLOT;
		}
		if(outputSlot == null) return false;
		if(!doSmelt) return true;
		
		itemstacks[INPUT_SLOT].stackSize --;
		if(itemstacks[INPUT_SLOT].stackSize <= 0)
			itemstacks[INPUT_SLOT] = null;
		if(itemstacks[outputSlot] == null)
			itemstacks[outputSlot] = output.copy();
		else
			itemstacks[outputSlot].stackSize += output.stackSize;
		markDirty();
		return true;
	}
	
	/** Overridden functions **/
	/** Get the lang name **/
	@Override
	public String getName()
	{
		return "container.SoulFragments_Freezer.name";
	}
		
	/** Get the human readable "display" name from the lang file **/
	@Override
	public IChatComponent getDisplayName()
	{
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
		}
	
	@Override
	public boolean hasCustomName() { return false; }
	
	/** Returns the number of slots in the inventory **/
	@Override
	public int getSizeInventory()
	{
		return itemstacks.length;
	}
	
	/** Gets the stack of items in a slot **/
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return itemstacks[index];
	}

	/**
	 * Removes some items from a slot
	 * @param index The index of the slot to remove items from
	 * @param count The amount of items to remove
	 * @return Returns the items removed in an ItemStack
	 **/
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemsInSlot = getStackInSlot(index); //items in the slot
		ItemStack itemsRemoved; //items to remove from the slot
		
		if(itemsInSlot == null) return null; //if the slot is empty
		//if there's not enough items in slot to fulfill the items removed
		if(itemsInSlot.stackSize <= count)
		{
			itemsRemoved = itemsInSlot; //make them equal
			setInventorySlotContents(index, null); //remove everything from that slot
		}
		else //otherwise...
		{
			itemsRemoved = itemsInSlot.splitStack(count); //split the stack and put the items in itemsRemoved
			if(itemsInSlot.stackSize == 0) //if we removed everything 
				setInventorySlotContents(index, null); //set the slot as empty
		}
		markDirty(); //mark as dirty, so the NBT knows what to save
		return itemsRemoved; //return the itemstack
	}
	
	/**
	 * Returns entire stacks of items in slot used by 
	 * crafting tables and such blocks that drops everything on closing
	 * @param index The index of the slot
	 * @return Returns everything inside the slots
	**/
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		ItemStack itemstack = getStackInSlot(index);
		if(itemstack != null) setInventorySlotContents(index, null);
		return itemstack;
	}
	
	/** Puts an item stack inside a specific slot **/
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		itemstacks[index] = stack; //set the stack contents first
		//if the stack exceeds the inventory stack limit
		if(stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit(); //make it smaller :-)
		markDirty(); //mark dirty
	}
	
	/** Default Minecraft inventory stack limit is 64 **/
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	/** Returns true if used by player, and follows this set of criteria
	* 1) If the tile entity hasn't been replaced
	* 2) If the player is close enough to the tile entity **/
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if(this.worldObj.getTileEntity(this.pos) != this) return false; //check if its the correct tile entity
		final double X_DISTANCE_OFFSET = 0.5;
		final double Y_DISTANCE_OFFSET = 0.5;
		final double Z_DISTANCE_OFFSET = 0.5;
		final double MAX_DISTANCE_SQ = 8.0 * 8.0; //maximum distance player is allowed to go (8 blocks) before closing gui
		//returns true if the player isn't too far away, i.e. player distance < max distance
		return player.getDistanceSq(X_DISTANCE_OFFSET + pos.getX(), Y_DISTANCE_OFFSET + pos.getY(), Z_DISTANCE_OFFSET + pos.getZ()) < MAX_DISTANCE_SQ;
	}

	@Override
	public void clear()
	{
		Arrays.fill(itemstacks, null);
	}

	/** Open GUI? **/
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new FreezerGUIInventory(playerInventory, this);
	}
	
	
	/** Called every tick to update the tileentity
	* i.e. if the fuel(s) has run out etc. **/
	@Override
	public void update()
	{
		if(freezingSomething()) //if the freezer is freezing something
		{
			freezeTime ++; //increase the freeze time
			currentFuelFreezeTime --; //decrease the fuel burning/freezing amount
			currentFuelBurnTime --;
		}
		//if the freezer is not freezing anything or the fuel ran out
		if(!freezingSomething() || currentFuelFreezeTime == 0 || currentFuelBurnTime == 0)
		{
			//check if there are anymore fuel, if not
			if(itemstacks[FUEL_SLOT] == null || itemstacks[FREEZER_SLOT] == null)
			{   //un-freeze everything at double the speed
				freezeTime = MathHelper.clamp_int(freezeTime - 2, 0, this.COOK_TIME_COMPLETION);
			}
			else
			{   
				//or else, reset the fuel times and consume one piece of fuel
				if(currentFuelFreezeTime == 0)
				{
					currentFuelFreezeTime = getFuelFreezeTime(itemstacks[FREEZER_SLOT]);
					itemstacks[FREEZER_SLOT].stackSize --;
				}
				if(currentFuelBurnTime == 0)
				{
					currentFuelBurnTime = getFuelTime(itemstacks[FUEL_SLOT]);
					itemstacks[FUEL_SLOT].stackSize --;
				}
			}
		}
		else
		{
			if(freezeTime == this.COOK_TIME_COMPLETION) //check if the item is finished freezing
			{
				freezeTime = 0; //reset timer
				freezeObject(true); //convert the item
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound); //Needed
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.itemstacks.length; i++)
		{
			//For each slot in the freezer, place it into the tag-list
			if(this.itemstacks[i] != null)
			{
				NBTTagCompound dataTag = new NBTTagCompound();
				dataTag.setByte("Slot", (byte) i);
				this.itemstacks[i].writeToNBT(dataTag);
				list.appendTag(dataTag);
			}
		}
		
		compound.setTag("Slots", list);
		compound.setTag("FuelFreeze", new NBTTagInt(currentFuelFreezeTime));
		compound.setTag("FuelBurn", new NBTTagInt(currentFuelBurnTime));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		final byte NBT_TYPE_COMPOUND = 10; //Something here. Apparently its 10...
		NBTTagList list = compound.getTagList("Slots", NBT_TYPE_COMPOUND);
		
		//Empty all slots
		Arrays.fill(itemstacks, null);
		for(int i = 0; i < list.tagCount(); i ++)
		{
			NBTTagCompound slotData = new NBTTagCompound();
			byte slotNumber = slotData.getByte("Slot");
			if(slotNumber >= 0 && slotNumber < this.itemstacks.length)
				this.itemstacks[slotNumber] = ItemStack.loadItemStackFromNBT(slotData);
		}
		
		currentFuelFreezeTime = compound.getInteger("FuelFreeze");
		currentFuelBurnTime = compound.getInteger("FuelBurn");
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		final int MetaData = 0;
		return new S35PacketUpdateTileEntity(this.pos, MetaData, compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}
	
	private final static byte FREEZE_FIELD_ID = 0;
	private final static byte BURN_TIME_ID = 1;
	private final static byte FREEZE_TIME_ID = 2;
	
	@Override
	public int getField(int id)
	{
		if(id == FREEZE_FIELD_ID) return freezeTime;
		if(id == BURN_TIME_ID) return currentFuelBurnTime;
		if(id == FREEZE_TIME_ID) return currentFuelFreezeTime;
		System.err.println("Invalid id for TileEntityFreezer.getField: " + id);
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		if(id == FREEZE_FIELD_ID) freezeTime = value;
		else if(id == BURN_TIME_ID) currentFuelBurnTime = value;
		else if(id == FREEZE_TIME_ID) currentFuelFreezeTime = value;
		else
			System.err.println("Invalid id for TileEntityFreezer.setField: " + id);
	}

	@Override
	public int getFieldCount()
	{
		return 3;
	}
	
	/** ---------------------------------------------------------------
	* These methods are not used by this block, but since its part of
	* IInventory, they must be implemented **/
	@Override
	public void openInventory(EntityPlayer player){ }
	
	@Override
	public void closeInventory(EntityPlayer player){ }
	
	/** Returns true if item is allowed to go into the slot **/
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return index == 3 ? false : (index != 0 ? isItemFuel(stack) : true);
	}
	/**--------------------------------------------------------------- **/

	@Override
	public String getGuiID()
	{
		return "soulfragments:freezer"; //TODO fill this in
	}
	
	/** Gets the slot number for the given face. i.e. for the furnace,
	 * a hopper going into the side (EnumFacing) fills the fuel slot (int) **/
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.UP ? SLOTS_TOP : (side == EnumFacing.DOWN ? SLOTS_BOTTOM : SLOTS_SIDE);
	}
	
	/**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: slot, item,
     * side
     */
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}
	
	/**
	 * @return Returns true if the itemstack in the given slot can be extracted from the given direction
	 */
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if(direction == EnumFacing.DOWN) //check if there's any output items
			return true;
		else if(direction == EnumFacing.UP) //disallow the input items to be extracted
			return false;
		else if(stack.getItem() == Items.bucket || isItemFuel(stack)) //disallow fuel to be extracted
			return false;
		return false; //Disallow if nothing returns
	}
}
