package com.soulteam.soulfrags;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomDrink extends Item
{
	private PotionEffect[] potion;
	private Item returnBottle;
	
	public CustomDrink(PotionEffect[] potion, Item returnItem)
	{
		this.potion = potion;
		this.returnBottle = returnItem;
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.DRINK;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer player) {
		/*if (!player.capabilities.isCreativeMode) {
			itemStack.stackSize = itemStack.stackSize - 1;
		}*/

		if (!world.isRemote) {
			for (int i = 0; i < potion.length; i++) {
				if (potion[i] != null && potion[i].getPotionID() > 0 && !world.isRemote) {
					player.addPotionEffect(new PotionEffect(potion[i]));
				}
			}
		}

		/*if (!player.capabilities.isCreativeMode) {
			if (itemStack.stackSize <= 0) {
				return new ItemStack(returnBottle);
			}

			player.inventory.addItemStackToInventory(new ItemStack(returnBottle));
		}*/

		return itemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}
	
	//@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {

            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            return itemStack;

    }

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack p_77636_1_, int pass)
	{
		if (pass == 0) {
			return true;
		} else {
			return false;
		}
	}
}
