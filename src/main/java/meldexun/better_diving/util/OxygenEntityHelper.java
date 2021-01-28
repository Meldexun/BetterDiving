package meldexun.better_diving.util;

import meldexun.better_diving.api.capability.ICapabilityOxygen;
import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygenProvider;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

public class OxygenEntityHelper {

	/**
	 * Returns the amount of oxygen this item currently holds.
	 */
	public static int getOxygen(Entity entity) {
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = entity.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.getOxygen();
	}

	/**
	 * Returns the amount of oxygen this item can hold.
	 */
	public static int getOxygenCapacity(Entity entity) {
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = entity.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.getOxygenCapacity();
	}

	/**
	 * Returns the amount of oxygen that was received.
	 */
	public static int receiveOxygen(Entity entity, int amount) {
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = entity.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.receiveOxygen(amount);
	}

	/**
	 * Returns the amount of oxygen that was extracted.
	 */
	public static int extractOxygen(Entity entity, int amount) {
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = entity.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.extractOxygen(amount);
	}

}
