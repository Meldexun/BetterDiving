package meldexun.better_diving.capability.oxygen;

public interface ICapabilityOxygen {

	public void setOxygen(int oxygen);

	public int getOxygen();

	public int receiveOxygen(int amount);

	public int extractOxygen(int amount);

	public int getOxygenCapacity();

}
