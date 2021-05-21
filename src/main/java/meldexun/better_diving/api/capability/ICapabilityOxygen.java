package meldexun.better_diving.api.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

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
	default int receiveOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygenCapacity() - this.getOxygen());
		this.setOxygen(this.getOxygen() + amount);
		return amount;
	}

	/**
	 * Returns the amount of oxygen that was extracted.
	 */
	default int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygen());
		this.setOxygen(this.getOxygen() - amount);
		return amount;
	}

	/**
	 * Returns if this object can be used by the specified player.
	 */
	default boolean canBeUsed(PlayerEntity player) {
		return true;
	}

}
