package meldexun.better_diving.capability.item.inventory;

import meldexun.better_diving.capability.inventory.CapabilityItemHandlerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityItemHandlerItem extends ItemStackHandler {

	private final ItemStack stack;
	private boolean hasBeenDeserialized = false;

	public CapabilityItemHandlerItem() {
		this(ItemStack.EMPTY, 1);
	}

	public CapabilityItemHandlerItem(ItemStack stack, int size) {
		super(size);
		this.stack = stack;
		if (!this.stack.hasTagCompound()) {
			this.stack.setTagCompound(new NBTTagCompound());
		}
		if (this.stack.getTagCompound().hasKey(CapabilityItemHandlerProvider.LOCATION_ITEM_STACK_HANDLER.toString())) {
			this.deserializeNBT(null);
		} else {
			this.serializeNBT();
		}
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (!this.hasBeenDeserialized) {
			this.deserializeNBT(null);
		}
		return super.getStackInSlot(slot);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		if (this.stack.hasTagCompound()) {
			this.stack.getTagCompound().setTag(CapabilityItemHandlerProvider.LOCATION_ITEM_STACK_HANDLER.toString(), super.serializeNBT());
		}
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (this.stack.hasTagCompound()) {
			super.deserializeNBT(this.stack.getTagCompound().getCompoundTag(CapabilityItemHandlerProvider.LOCATION_ITEM_STACK_HANDLER.toString()));
			this.hasBeenDeserialized = true;
		}
	}

	@Override
	protected void onContentsChanged(int slot) {
		this.serializeNBT();
	}

}
