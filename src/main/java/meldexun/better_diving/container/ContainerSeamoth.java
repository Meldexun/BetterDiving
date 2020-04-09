package meldexun.better_diving.container;

import javax.annotation.Nullable;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.item.ItemPowerCell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSeamoth extends Container {

	private ItemStack stack;
	private EnumHand hand;

	public ContainerSeamoth(InventoryPlayer playerInv, EntitySeamoth entity) {
		this.stack = null;
		this.hand = null;
		IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}

		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 62, 50) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}

			@Override
			@Nullable
			@SideOnly(Side.CLIENT)
			public String getSlotTexture() {
				return "better_diving:items/empty_power_cell";
			}
		});

		for (int i = 0; i < 4; i++) {
			this.addSlotToContainer(new SlotItemHandler(inventory, 1 + i, 152, 8 + i * 18) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}

				@Override
				@Nullable
				@SideOnly(Side.CLIENT)
				public String getSlotTexture() {
					return "better_diving:items/empty_upgrade";
				}
			});
		}
	}

	public ContainerSeamoth(InventoryPlayer playerInv, ItemStack stack, EnumHand hand) {
		this.stack = stack;
		this.hand = hand;
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

		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 62, 50) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}

			@Override
			@Nullable
			@SideOnly(Side.CLIENT)
			public String getSlotTexture() {
				return "better_diving:items/empty_power_cell";
			}
		});

		for (int i = 0; i < 4; i++) {
			this.addSlotToContainer(new SlotItemHandler(inventory, 1 + i, 152, 8 + i * 18) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}

				@Override
				@Nullable
				@SideOnly(Side.CLIENT)
				public String getSlotTexture() {
					return "better_diving:items/empty_upgrade";
				}
			});
		}
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

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		if (this.stack != null && this.hand != null) {
			playerIn.setHeldItem(this.hand, this.stack);
		}
	}

}
