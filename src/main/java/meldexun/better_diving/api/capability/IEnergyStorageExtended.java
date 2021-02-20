package meldexun.better_diving.api.capability;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageExtended extends IEnergyStorage {

	void setEnergy(int energy);

	double getEnergyPercent();

}
