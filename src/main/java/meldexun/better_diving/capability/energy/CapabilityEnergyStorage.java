package meldexun.better_diving.capability.energy;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

public class CapabilityEnergyStorage extends EnergyStorage {

	public CapabilityEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	public void setEnergy(int energy) {
		this.energy = MathHelper.clamp(energy, 0, this.capacity);
	}

	public double getEnergyPercentage() {
		return 100.0D * (double) this.energy / (double) this.capacity;
	}

}
