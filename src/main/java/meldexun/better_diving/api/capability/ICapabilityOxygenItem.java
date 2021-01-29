package meldexun.better_diving.api.capability;

import net.minecraft.inventory.EquipmentSlotType;

public interface ICapabilityOxygenItem extends ICapabilityOxygen {

	/**
	 * Returns whether or not this item can be used when worn in the specified slot.
	 */
	boolean isValidSlot(EquipmentSlotType slot);

	/**
	 * Returns if a diving mask is needed when using this item.
	 */
	boolean needsDivingMask();

}
