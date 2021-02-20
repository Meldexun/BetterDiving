package meldexun.better_diving.capability.inventory.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityItemHandlerItem extends ItemStackHandler {

	private final ItemStack stack;
	private boolean hasBeenDeserialized = false;

	public CapabilityItemHandlerItem(ItemStack stack, int size) {
		super(size);
		this.stack = stack;
		CompoundNBT nbt = this.stack.getOrCreateTag();
		if (nbt.contains(CapabilityItemHandlerItemProvider.REGISTRY_NAME.toString(), Constants.NBT.TAG_COMPOUND)) {
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
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = this.stack.getOrCreateTag();
		nbt.put(CapabilityItemHandlerItemProvider.REGISTRY_NAME.toString(), super.serializeNBT());
		return null;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		CompoundNBT nbt1 = this.stack.getOrCreateTag();
		super.deserializeNBT(nbt1.getCompound(CapabilityItemHandlerItemProvider.REGISTRY_NAME.toString()));
		this.hasBeenDeserialized = true;
	}

	@Override
	public void onContentsChanged(int slot) {
		this.serializeNBT();
	}

}
