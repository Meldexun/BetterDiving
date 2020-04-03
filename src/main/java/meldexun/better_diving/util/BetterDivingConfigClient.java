package meldexun.better_diving.util;

public class BetterDivingConfigClient {

	public static boolean itemEntityMovement = BetterDivingConfig.GENERAL.itemEntityMovement;
	public static double itemEntityMovementFactor = BetterDivingConfig.GENERAL.itemEntityMovementFactor;
	public static boolean oxygenSyncPackets = BetterDivingConfig.GENERAL.oxygenSyncPackets;
	public static boolean seamothEnergySyncPackets = BetterDivingConfig.GENERAL.seamothEnergySyncPackets;
	public static boolean vanillaDivingMovement = BetterDivingConfig.GENERAL.vanillaDivingMovement;
	public static double swimSpeedLimitLower = BetterDivingConfig.GENERAL.swimSpeedLimitLower;
	public static double swimSpeedLimitUpper = BetterDivingConfig.GENERAL.swimSpeedLimitUpper;

	public static boolean blockBreaking = BetterDivingConfig.MODULES.blockBreaking;
	public static boolean divingMovement = BetterDivingConfig.MODULES.divingMovement;
	public static boolean oxygenHandling = BetterDivingConfig.MODULES.oxygenHandling;

	public static boolean airEfficiency = BetterDivingConfig.DIVING_VALUES.airEfficiency;
	public static int airEfficiencyLimit = BetterDivingConfig.DIVING_VALUES.airEfficiencyLimit;
	public static double seamothSpeed = BetterDivingConfig.DIVING_VALUES.seamothSpeed;
	public static int seamothEnergyUsage = BetterDivingConfig.DIVING_VALUES.seamothEnergyUsage;

	public static void update() {
		itemEntityMovement = BetterDivingConfig.GENERAL.itemEntityMovement;
		itemEntityMovementFactor = BetterDivingConfig.GENERAL.itemEntityMovementFactor;
		oxygenSyncPackets = BetterDivingConfig.GENERAL.oxygenSyncPackets;
		seamothEnergySyncPackets = BetterDivingConfig.GENERAL.seamothEnergySyncPackets;
		vanillaDivingMovement = BetterDivingConfig.GENERAL.vanillaDivingMovement;
		swimSpeedLimitLower = BetterDivingConfig.GENERAL.swimSpeedLimitLower;
		swimSpeedLimitUpper = BetterDivingConfig.GENERAL.swimSpeedLimitUpper;

		blockBreaking = BetterDivingConfig.MODULES.blockBreaking;
		divingMovement = BetterDivingConfig.MODULES.divingMovement;
		oxygenHandling = BetterDivingConfig.MODULES.oxygenHandling;

		airEfficiency = BetterDivingConfig.DIVING_VALUES.airEfficiency;
		airEfficiencyLimit = BetterDivingConfig.DIVING_VALUES.airEfficiencyLimit;
		seamothSpeed = BetterDivingConfig.DIVING_VALUES.seamothSpeed;
		seamothEnergyUsage = BetterDivingConfig.DIVING_VALUES.seamothEnergyUsage;
	}

}
