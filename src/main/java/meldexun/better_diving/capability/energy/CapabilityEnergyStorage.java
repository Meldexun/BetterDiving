package meldexun.better_diving.capability.energy;

import meldexun.better_diving.api.capability.IEnergyStorageExtended;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

public class CapabilityEnergyStorage extends EnergyStorage implements IEnergyStorageExtended {

	public CapabilityEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	@Override
	public void setEnergy(int energy) {
		this.energy = MathHelper.clamp(energy, 0, this.getMaxEnergyStored());
	}

	@Override
	public double getEnergyPercent() {
		return (double) this.getEnergyStored() / (double) this.getMaxEnergyStored();
	}

}
