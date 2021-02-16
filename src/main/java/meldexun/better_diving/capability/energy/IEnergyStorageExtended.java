package meldexun.better_diving.capability.energy;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageExtended extends IEnergyStorage {

	void setEnergy(int energy);

	int getEnergyPercent();

}
