package meldexun.better_diving.container;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.item.ItemPowerCell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSeamoth extends Container {

	public ContainerSeamoth(InventoryPlayer playerInv, EntitySeamoth entity) {
		IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}

		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 53, 36) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}
		});
	}

	public ContainerSeamoth(InventoryPlayer playerInv, ItemStack stack) {
		IItemHandler inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			if (k == playerInv.currentItem) {
				this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142) {
					@Override
					public boolean canTakeStack(EntityPlayer playerIn) {
						return false;
					}
				});
			} else {
				this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
			}
		}

		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 53, 36) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}
		});
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index > 35) {
				if (!this.mergeItemStack(itemstack1, 0, 35, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), false)) {
				return ItemStack.EMPTY;
			}

		}
		return itemstack;
	}

}
