package meldexun.better_diving.inventory.container;

import meldexun.better_diving.BetterDiving;
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
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}

			@Override
			public void setChanged() {
				ContainerSeamoth.this.onSeamothSlotChanged(this.getSlotIndex());
			}
		}.setBackground(PlayerContainer.BLOCK_ATLAS, new ResourceLocation(BetterDiving.MOD_ID, "item/empty_power_cell")));
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
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof ItemPowerCell;
			}
		}.setBackground(PlayerContainer.BLOCK_ATLAS, new ResourceLocation(BetterDiving.MOD_ID, "item/empty_power_cell")));
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return true;
	}

	
	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		Slot slot = this.slots.get(index);

		if (slot == null) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getItem();

		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}

		if (index > 35) {
			if (!this.moveItemStackTo(stack, 0, 35, false)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.moveItemStackTo(stack, 36, this.slots.size(), false)) {
			return ItemStack.EMPTY;
		}

		slot.setChanged();
		return stack;
	}

	protected void onSeamothSlotChanged(int slot) {

	}

}
