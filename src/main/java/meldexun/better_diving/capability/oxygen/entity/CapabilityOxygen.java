package meldexun.better_diving.capability.oxygen.entity;

import meldexun.better_diving.api.capability.ICapabilityOxygen;
import net.minecraft.util.math.MathHelper;

public class CapabilityOxygen implements ICapabilityOxygen {

	protected final int oxygenCapacity;
	protected int oxygen;

	public CapabilityOxygen(int oxygenCapacity) {
		this.oxygenCapacity = oxygenCapacity;
		this.oxygen = oxygenCapacity;
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

	@Override
	public int receiveOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygenCapacity() - this.oxygen);
		this.oxygen = this.oxygen + amount;
		return amount;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.oxygen);
		this.oxygen = this.oxygen - amount;
		return amount;
	}

}
