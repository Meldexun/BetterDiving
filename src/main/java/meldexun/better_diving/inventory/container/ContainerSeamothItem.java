package meldexun.better_diving.inventory.container;

import meldexun.better_diving.capability.inventory.item.CapabilityItemHandlerItem;
import meldexun.better_diving.init.BetterDivingContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerSeamothItem extends ContainerSeamoth {

	private final IItemHandler seamothInv;
	private final IntReferenceHolder hand = IntReferenceHolder.single();

	/** Server */
	public ContainerSeamothItem(int id, PlayerInventory playerInv, ItemStack stack, Hand hand) {
		super(BetterDivingContainers.SEAMOTH_ITEM.get(), id, playerInv, stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new));
		this.seamothInv = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		this.trackInt(this.hand);
		this.hand.set(hand.ordinal());
		int i = 27 + playerInv.currentItem;
		Slot slot = this.inventorySlots.get(i);
		this.inventorySlots.set(i, new Slot(playerInv, playerInv.currentItem, slot.xPos, slot.yPos) {
			@Override
			public boolean canTakeStack(PlayerEntity playerIn) {
				return ContainerSeamothItem.this.hand.get() != 0;
			}
		});
		this.inventorySlots.get(i).slotNumber = i;
	}

	/** Client */
	public ContainerSeamothItem(int id, PlayerInventory playerInv) {
		super(BetterDivingContainers.SEAMOTH_ITEM.get(), id, playerInv);
		this.seamothInv = null;
		this.trackInt(this.hand);
		int i = 27 + playerInv.currentItem;
		Slot slot = this.inventorySlots.get(i);
		this.inventorySlots.set(i, new Slot(playerInv, playerInv.currentItem, slot.xPos, slot.yPos) {
			@Override
			public boolean canTakeStack(PlayerEntity playerIn) {
				return ContainerSeamothItem.this.hand.get() != 0;
			}
		});
		this.inventorySlots.get(i).slotNumber = i;
	}

	public int getHand() {
		return this.hand.get();
	}

	@Override
	protected void onSeamothSlotChanged(int slot) {
		if (this.seamothInv instanceof CapabilityItemHandlerItem) {
			((CapabilityItemHandlerItem) this.seamothInv).onContentsChanged(slot);
		}
	}

}
