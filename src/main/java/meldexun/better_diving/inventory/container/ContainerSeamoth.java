package meldexun.better_diving.inventory.container;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.inventory.item.CapabilityItemHandlerItem;
import meldexun.better_diving.item.ItemPowerCell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSeamoth extends Container {

	/** Server */
	public ContainerSeamoth(ContainerType<?> type, int id, PlayerInventory playerInv, IItemHandler seamothInv) {
		super(type, id);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
		}

		this.addSlot(new SlotItemHandler(seamothInv, 0, 62, 50) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}

			@Override
			public void onSlotChanged() {
				if (seamothInv instanceof CapabilityItemHandlerItem) {
					((CapabilityItemHandlerItem) seamothInv).onContentsChanged(0);
				}
			}
		}.setBackground(PlayerContainer.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(BetterDiving.MOD_ID, "item/empty_power_cell")));
	}

	/** Client */
	public ContainerSeamoth(ContainerType<?> type, int id, PlayerInventory playerInv) {
		super(type, id);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
		}

		IInventory seamothInv = new Inventory(1);
		this.addSlot(new Slot(seamothInv, 0, 62, 50) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}
		}.setBackground(PlayerContainer.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(BetterDiving.MOD_ID, "item/empty_power_cell")));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);

		if (slot == null) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getStack();

		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}

		if (index > 35) {
			if (!this.mergeItemStack(stack, 0, 35, false)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.mergeItemStack(stack, 36, this.inventorySlots.size(), false)) {
			return ItemStack.EMPTY;
		}

		slot.onSlotChanged();
		return stack;
	}

}
