package meldexun.better_diving.api.capability;

import net.minecraft.entity.player.PlayerEntity;

public interface ICapabilityOxygen {

	/**
	 * Sets the amount of oxygen this object currently holds.
	 */
	void setOxygen(int amount);

	/**
	 * Returns the amount of oxygen this object currently holds.
	 */
	int getOxygen();

	/**
	 * Returns the amount of oxygen this object can hold.
	 */
	int getOxygenCapacity();

	/**
	 * Returns the amount of oxygen that was received.
	 */
	int receiveOxygen(int amount);

	/**
	 * Returns the amount of oxygen that was extracted.
	 */
	int extractOxygen(int amount);

	/**
	 * Returns if this object can be used by the specified player.
	 */
	default boolean canBeUsed(PlayerEntity player) {
		return true;
	}

}
