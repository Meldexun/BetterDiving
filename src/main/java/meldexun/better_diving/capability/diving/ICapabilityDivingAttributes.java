package meldexun.better_diving.capability.diving;

import net.minecraft.entity.player.EntityPlayer;

public interface ICapabilityDivingAttributes {

	public void changeEquip(EntityPlayer player);

	public void tick(EntityPlayer player);

	public void setOxygen(int oxygen);

	public int getOxygen();

	public int receiveOxygen(int amount);

	public int extractOxygen(int amount);

	public void setPrevOxygen(int oxygen);

	public int getPrevOxygen();

	public void setOxygenCapacity(int oxygen);

	public int getOxygenCapacity();

	public void setSwimSpeedBase(double speed);

	public double getSwimSpeedBase();

	public void setSwimSpeedBonus(double speed);

	public double getSwimSpeedBonus();

	public void setBreakSpeed(float speed);

	public float getBreakSpeed();

	public int getOxygenFromPlayer(EntityPlayer player);

	public int getOxygenCapacityFromPlayer(EntityPlayer player);

	public int receiveOxygenFromPlayer(EntityPlayer player, int amount);

	public int extractOxygenFromPlayer(EntityPlayer player, int amount);

	public double getOxygenPercent(EntityPlayer player);

}
