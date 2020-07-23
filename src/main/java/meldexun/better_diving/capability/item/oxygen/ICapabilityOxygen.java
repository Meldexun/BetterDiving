package meldexun.better_diving.capability.item.oxygen;

public interface ICapabilityOxygen {

	void setOxygen(int oxygen);

	int getOxygen();

	int receiveOxygen(int amount);

	int extractOxygen(int amount);

	int getOxygenCapacity();

}
