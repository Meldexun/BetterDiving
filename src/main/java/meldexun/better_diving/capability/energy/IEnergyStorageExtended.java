package meldexun.better_diving.capability.energy;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageExtended extends IEnergyStorage {

	public void setEnergy(int energy);

	public int getEnergyPercent();

}
