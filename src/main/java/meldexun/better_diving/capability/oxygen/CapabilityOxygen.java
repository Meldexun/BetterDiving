package meldexun.better_diving.capability.oxygen;

import net.minecraft.util.math.MathHelper;

public class CapabilityOxygen implements ICapabilityOxygen {

	private final int oxygenCapacity;
	private int oxygen;

	public CapabilityOxygen() {
		this(0, 0);
	}

	public CapabilityOxygen(int oxygenCapacity, int oxygen) {
		this.oxygenCapacity = oxygenCapacity;
		this.oxygen = oxygen;
	}

	@Override
	public void setOxygen(int oxygen) {
		this.oxygen = oxygen;
	}

	@Override
	public int getOxygen() {
		return this.oxygen;
	}

	@Override
	public int receiveOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.oxygenCapacity - this.oxygen);
		this.oxygen += amount;
		return amount;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.oxygen);
		this.oxygen -= amount;
		return amount;
	}

	@Override
	public int getOxygenCapacity() {
		return this.oxygenCapacity;
	}

}
