package meldexun.better_diving.init;

import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.oxygen.CapabilityOxygenProvider;

public class ModCapabilities {

	public static void registerCapabilities() {
		CapabilityDivingAttributesProvider.register();
		CapabilityOxygenProvider.register();
	}

}
