package meldexun.better_diving.init;

import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygenProvider;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItemProvider;

public class BetterDivingCapabilities {

	public static void registerCapabilities() {
		CapabilityOxygenProvider.register();
		CapabilityOxygenItemProvider.register();
	}

}
