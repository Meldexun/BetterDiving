package meldexun.better_diving.capability.oxygen.item;

import java.util.Set;

import meldexun.better_diving.api.capability.ICapabilityOxygenItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

public class CapabilityOxygenItem implements ICapabilityOxygenItem {

	private final ItemStack stack;
	private final int oxygenCapacity;
	private final Set<EquipmentSlotType> slots;
	private final boolean needsDivingMask;

	public CapabilityOxygenItem(ItemStack stack, int oxygenCapacity, Set<EquipmentSlotType> slots, boolean needsDivingMask) {
		this.stack = stack;
		this.oxygenCapacity = oxygenCapacity;
		this.slots = slots;
		this.needsDivingMask = needsDivingMask;
	}

	@Override
	public void setOxygen(int amount) {
		CompoundNBT nbt = this.stack.getTag();
		if (nbt == null) {
			nbt = new CompoundNBT();
			this.stack.setTag(nbt);
		}
		nbt.putInt(CapabilityOxygenItemProvider.REGISTRY_NAME.toString(), MathHelper.clamp(amount, 0, this.getOxygenCapacity()));
	}

	@Override
	public int getOxygen() {
		CompoundNBT nbt = this.stack.getTag();
		return nbt != null ? nbt.getInt(CapabilityOxygenItemProvider.REGISTRY_NAME.toString()) : 0;
	}

	@Override
	public int getOxygenCapacity() {
		return this.oxygenCapacity;
	}

	@Override
	public int receiveOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygenCapacity() - this.getOxygen());
		this.setOxygen(this.getOxygen() + amount);
		return amount;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygen());
		this.setOxygen(this.getOxygen() - amount);
		return amount;
	}

	@Override
	public boolean isValidSlot(EquipmentSlotType slot) {
		return this.slots.contains(slot);
	}

	@Override
	public boolean needsDivingMask() {
		return this.needsDivingMask;
	}

}
