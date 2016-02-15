package com.soulteam.soulfrags.GUI;

import com.soulteam.soulfrags.blocks.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FreezerGUIInventory extends Container
{
	//Store tile entity for use :)
	private TileEntityFreezer tilefreezer;
	
	/* We need to store each slot *index* used by this GUI
	We have the player inventory slots beneath the freezer slots
	The player inventory slots contain a 9-slot hotbar and a 3x9 slot inventory
	space. The freezer has an input slot, a fuel slot, a freezer slot and an output slot
	So, the index goes like this:
		0-8: The 9 hotbar slots, 9-35: The 27 inventory slots
		36: The input, 37: The fuel, 39: The freezer and 40: The output
	*/
	private final int HOTBAR_COUNT = 9;
	private final int INV_ROW_COUNT = 3;
	private final int INV_COLUMN_COUNT = 9;
	private final int INV_COUNT = INV_ROW_COUNT * INV_COLUMN_COUNT;
	
	private final int INPUT_INDEX = INV_COUNT + HOTBAR_COUNT + 1; //36
	private final int FUEL_INDEX = INPUT_INDEX + 1; //37
	private final int FREEZER_INDEX = FUEL_INDEX + 1; //38
	private final int OUTPUT_INDEX = FREEZER_INDEX + 1; //39
	
	//These are the index of the slots WITHIN each component
	//So, the index of the (playerInv) inventory slots are 0-35, and the index of the Freezer slots will be 0-3
	private final int INPUT_NUMBER = 0;
	private final int FUEL_NUMBER = INPUT_NUMBER + 1;
	private final int FREEZER_NUMBER = FUEL_NUMBER + 1;
	private final int OUTPUT_NUMBER = FREEZER_NUMBER + 1;
	
	public FreezerGUIInventory(InventoryPlayer invPlayer, TileEntityFreezer tileEntityFreezer)
	{
		this.tilefreezer = tileEntityFreezer;
		//GUI scale is 176x166 (length, width)
		final int SLOT_HOTBAR_X = 7;
		final int SLOT_HOTBAR_Y = 141;
		
		final int SLOT_INVENT_X = 7;
		final int SLOT_INVENT_Y = 83;
		//Now fill in the slots for the hotbar
		for(int i = 0; i < HOTBAR_COUNT; i++)
		{
			int slotnumber = i;
			addSlotToContainer(new Slot(invPlayer, slotnumber, SLOT_HOTBAR_X + 18 * i, SLOT_HOTBAR_Y));
		}
		
		//Now fill in the inventory slots
		for(int y = 0; y < INV_COLUMN_COUNT; y++)
		{
			for(int x = 0; x < INV_ROW_COUNT; x++)
			{
				int slotnumber = x + HOTBAR_COUNT + y * INV_COLUMN_COUNT;
				int xpos = SLOT_INVENT_X + x * 18;
				int ypos = SLOT_INVENT_Y + y * 18;
				addSlotToContainer(new Slot(invPlayer, slotnumber, xpos, ypos));
			}
		}
		
		// Now add the fuel, i/o and freezer slots
		// Coordinates retrieved from GIMP :p
		addSlotToContainer(new Slot(invPlayer, INPUT_INDEX, 25, 22));
		addSlotToContainer(new Slot(invPlayer, FUEL_INDEX, 25, 58));
		addSlotToContainer(new Slot(invPlayer, FREEZER_INDEX, 79, 58));
		addSlotToContainer(new Slot(invPlayer, OUTPUT_INDEX, 133, 40));
	}
	
	/** If player is still using the thing. Otherwise, close the GUI. **/
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return tilefreezer.isUseableByPlayer(playerIn);
	}
	
	/**
	 * This method is activated when the player shift-clicks on a slot
	 * The return statement 
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		//Get the source slot from the list of inventory slots
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		//If there's nothing in the slot, then return null
		if(sourceSlot == null || !sourceSlot.getHasStack()) return null;
		
		//Get the source stack from the slot and grab a copy of it too
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack sourceStackCopy = sourceStack.copy();
		
		//Check if the source slot index is between (including 0 and 35) indexes 0 and 35
		//In other words, check if the slot index belongs to a vanilla slot
		if(sourceSlotIndex >= 0 && sourceSlotIndex <= 35)
		{
			//Check if the item is valid for the slot, then check if the item is not
			//merge-able with the slot. If true, return null.
			if(tilefreezer.getFreezeRecipe(sourceStack) != null)
			{
				//If we can't merge the stack to the input slot, return null
				if(!mergeItemStack(sourceStack, INPUT_INDEX, INPUT_INDEX, false))
					return null;
			}
			else if(tilefreezer.getFuelTime(sourceStack) > 0)
			{
				if(!mergeItemStack(sourceStack, FUEL_INDEX, FUEL_INDEX, false))
					return null;
			}
			else if(tilefreezer.getFuelFreezeTime(sourceStack) > 0)
			{
				if(!mergeItemStack(sourceStack, FREEZER_INDEX, FREEZER_INDEX, false));
					return null;
			}
			else
				return null;
		}
		//Now, check if the player shift-clicked on one of the freezer slots (non-vanilla)
		//If true, check if the itemstack is not merge-able with existing vanilla slots. If true
		//Return null.
		else if(sourceSlotIndex >= INPUT_INDEX && sourceSlotIndex <= OUTPUT_INDEX)
		{
			if(!mergeItemStack(sourceStack, 0, 35, false))
				return null;
		}
		//Or else, return null
		else
		{
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return null;
		}
		//When everything passes the conditions, pickup the stack
		//And return the copy of the source slot.
		sourceSlot.onPickupFromSlot(player, sourceStack);
		return sourceStackCopy;
	}
	
	/** Client Synchronization **/
	/** This is where you check if any values have changed and if so send an update to any clients accessing this container
	* The container item stacks are tested in Container.detectAndSendChanges, so we don't need to do that
	* We iterate through all of the TileEntity Fields to find any which have changed, and send them.
	* You don't have to use fields if you don't wish to; just manually match the ID in sendProgressBarUpdate with the value in
	* updateProgressBar()
	* The progress bar values are restricted to shorts.  If you have a larger value (eg int), it's not a good idea to try and split it
	* up into two shorts because the progress bar values are sent independently, and unless you add synchronization logic at the
	* receiving side, your int value will be wrong until the second short arrives.  Use a custom packet instead. **/
	@Override
	public void detectAndSendChanges()
	{
		
	}
}
