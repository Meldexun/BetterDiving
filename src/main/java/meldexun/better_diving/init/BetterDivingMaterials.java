package meldexun.better_diving.init;

import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.item.CustomArmorMaterial;

public class BetterDivingMaterials {

	public static class ArmorMaterials {

		public static final CustomArmorMaterial DIVING_GEAR = new CustomArmorMaterial("diving_gear", BetterDivingConfig.SERVER_CONFIG.divingGear);
		public static final CustomArmorMaterial IMPROVED_DIVING_GEAR = new CustomArmorMaterial("improved_diving_gear", BetterDivingConfig.SERVER_CONFIG.improvedDivingGear);
		public static final CustomArmorMaterial REINFORCED_DIVING_GEAR = new CustomArmorMaterial("reinforced_diving_gear", BetterDivingConfig.SERVER_CONFIG.reinforcedDivingGear);

	}

}
