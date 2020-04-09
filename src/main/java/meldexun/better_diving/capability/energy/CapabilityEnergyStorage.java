package meldexun.better_diving.capability.energy;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

public class CapabilityEnergyStorage extends EnergyStorage implements IEnergyStorageExtended {

	public CapabilityEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	@Override
	public void setEnergy(int energy) {
		this.energy = MathHelper.clamp(energy, 0, this.capacity);
	}

	@Override
	public int getEnergyPercent() {
		return (int) (100.0D * this.energy / this.capacity);
	}

}
