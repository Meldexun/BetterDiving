package meldexun.better_diving.api.capability;

import net.minecraft.inventory.EquipmentSlotType;

public interface ICapabilityOxygenItem extends ICapabilityOxygen {

	/**
	 * Returns whether or not this item can be used when worn in the specified slot.
	 */
	boolean isValidSlot(EquipmentSlotType slot);

	/**
	 * Only used when this item is equipped in the head slot!<br>
	 * Returns the depth in blocks which a player can dive before he receives a penalty on oxygen consumption.
	 */
	int getMaxDivingDepth();

}
