package meldexun.better_diving.capability.diving;

public interface ICapabilityDivingAttributes {

	public void tick();

	public void updateSize();

	/**
	 * Only used when reading the capability from NBT or when receiving oxygen sync
	 * packets.
	 * 
	 * @param oxygen
	 */
	public void setOxygen(int oxygen);

	/**
	 * Returns the amount of oxygen that the player has. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getOxygenFromPlayer()}
	 * 
	 * @return
	 */
	public int getOxygen();

	public int getOxygenFromPlayer();

	public double getOxygenFromPlayerInPercent();

	/**
	 * Adds oxygen to the player. Returns the amount of oxygen that was added. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #receiveOxygenFromPlayer()}
	 * 
	 * @param amount
	 * @return
	 */
	public int receiveOxygen(int amount);

	public int receiveOxygenFromPlayer(int amount);

	/**
	 * Removes oxygen from the player. Returns the amount of oxygen that was
	 * removed. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #extractOxygenFromPlayer()}
	 * 
	 * @param amount
	 * @return
	 */
	public int extractOxygen(int amount);

	public int extractOxygenFromPlayer(int amount);

	/**
	 * Returns the oxygen capacity of the player. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getOxygenCapacityFromPlayer()}
	 * 
	 * @return
	 */
	public int getOxygenCapacity();

	public int getOxygenCapacityFromPlayer();

	/**
	 * Returns the swim speed of the player. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getSwimSpeedFromPlayer()}
	 * 
	 * @return
	 */
	public double getSwimSpeed();

	public double getSwimSpeedFromPlayer();

	/**
	 * Returns the break speed of the player. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getBreakSpeedFromPlayer()}
	 * 
	 * @return
	 */
	public float getBreakSpeed();

	public float getBreakSpeedFromPlayer();

	// isDiving setter and getter

	public void setIsDiving(boolean isDiving);

	public boolean isDiving();

	public void setPrevIsDiving(boolean prevIsDiving);

	public boolean prevIsDiving();

	// divingTick setter and getter

	public void setDivingTick(float divingTick);

	public float getDivingTick();

	public void setPrevDivingTick(float prevDivingTick);

	public float getPrevDivingTick();

	// divingTickHorizontal setter and getter

	public void setDivingTickHorizontal(float divingTickHorizontal);

	public float getDivingTickHorizontal();

	public void setPrevDivingTickHorizontal(float prevDivingTickHorizontal);

	public float getPrevDivingTickHorizontal();

	// divingTickVertical setter and getter

	public void setDivingTickVertical(float divingTickVertical);

	public float getDivingTickVertical();

	public void setPrevDivingTickVertical(float prevDivingTickVertical);

	public float getPrevDivingTickVertical();

}
