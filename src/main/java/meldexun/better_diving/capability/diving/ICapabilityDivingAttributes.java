package meldexun.better_diving.capability.diving;

public interface ICapabilityDivingAttributes {

	void tick();

	void updateSize();

	/**
	 * Only used when reading the capability from NBT or when receiving oxygen sync
	 * packets.
	 * 
	 * @param oxygen
	 */
	void setOxygen(int oxygen);

	/**
	 * Returns the amount of oxygen that the player has. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getOxygenFromPlayer()}
	 * 
	 * @return
	 */
	int getOxygen();

	int getOxygenFromPlayer();

	double getOxygenFromPlayerInPercent();

	/**
	 * Adds oxygen to the player. Returns the amount of oxygen that was added. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #receiveOxygenFromPlayer()}
	 * 
	 * @param amount
	 * @return
	 */
	int receiveOxygen(int amount);

	int receiveOxygenFromPlayer(int amount);

	/**
	 * Removes oxygen from the player. Returns the amount of oxygen that was
	 * removed. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #extractOxygenFromPlayer()}
	 * 
	 * @param amount
	 * @return
	 */
	int extractOxygen(int amount);

	int extractOxygenFromPlayer(int amount);

	/**
	 * Returns the oxygen capacity of the player. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getOxygenCapacityFromPlayer()}
	 * 
	 * @return
	 */
	int getOxygenCapacity();

	int getOxygenCapacityFromPlayer();

	/**
	 * Returns the swim speed of the player. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getSwimSpeedFromPlayer()}
	 * 
	 * @return
	 */
	double getSwimSpeed();

	double getSwimSpeedFromPlayer();

	/**
	 * Returns the break speed of the player. <br>
	 * This does not take any gear into account. <br>
	 * See {@link #getBreakSpeedFromPlayer()}
	 * 
	 * @return
	 */
	float getBreakSpeed();

	float getBreakSpeedFromPlayer();

	// isDiving setter and getter

	void setIsDiving(boolean isDiving);

	boolean isDiving();

	void setPrevIsDiving(boolean prevIsDiving);

	boolean prevIsDiving();

	// divingTick setter and getter

	void setDivingTick(float divingTick);

	float getDivingTick();

	void setPrevDivingTick(float prevDivingTick);

	float getPrevDivingTick();

	// divingTickHorizontal setter and getter

	void setDivingTickHorizontal(float divingTickHorizontal);

	float getDivingTickHorizontal();

	void setPrevDivingTickHorizontal(float prevDivingTickHorizontal);

	float getPrevDivingTickHorizontal();

	// divingTickVertical setter and getter

	void setDivingTickVertical(float divingTickVertical);

	float getDivingTickVertical();

	void setPrevDivingTickVertical(float prevDivingTickVertical);

	float getPrevDivingTickVertical();

}
