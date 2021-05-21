package meldexun.better_diving.capability.oxygen.entity;

import meldexun.better_diving.api.capability.ICapabilityOxygen;
import net.minecraft.util.math.MathHelper;

public class CapabilityOxygen implements ICapabilityOxygen {

	private final int oxygenCapacity;
	private int oxygen;

	public CapabilityOxygen(int oxygenCapacity) {
		this.oxygenCapacity = oxygenCapacity;
		this.setOxygen(this.getOxygenCapacity());
	}

	@Override
	public void setOxygen(int amount) {
		this.oxygen = MathHelper.clamp(amount, 0, this.getOxygenCapacity());
	}

	@Override
	public int getOxygen() {
		return this.oxygen;
	}

	@Override
	public int getOxygenCapacity() {
		return this.oxygenCapacity;
	}

}
