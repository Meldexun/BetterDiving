package meldexun.better_diving.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class TileEntityEnergyGenerator extends TileEntity {

	private final int capacity;
	private final int maxReceive;
	private final int maxExtract;
	private final int energy;

	public TileEntityEnergyGenerator(int capacity, int maxReceive, int maxExtract, int energy) {
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.energy = energy;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getMaxReceive() {
		return this.maxReceive;
	}

	public int getMaxExtract() {
		return this.maxExtract;
	}

	public int getEnergy() {
		return this.energy;
	}

	public IEnergyStorage getEnergyCapability() {
		return this.getCapability(CapabilityEnergy.ENERGY, null);
	}

	public int getEnergyStored() {
		IEnergyStorage ienergy = this.getEnergyCapability();
		return ienergy.getEnergyStored();
	}

	public int getMaxEnergyStored() {
		IEnergyStorage ienergy = this.getEnergyCapability();
		return ienergy.getMaxEnergyStored();
	}

	public int receiveEnergy(int amount) {
		IEnergyStorage ienergy = this.getEnergyCapability();
		return ienergy.receiveEnergy(amount, false);
	}

	public int extractEnergy(int amount) {
		IEnergyStorage ienergy = this.getEnergyCapability();
		return ienergy.extractEnergy(amount, false);
	}

}
