package meldexun.better_diving.init;

import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.item.oxygen.CapabilityOxygenProvider;

public class ModCapabilities {

	private ModCapabilities() {

	}

	public static void registerCapabilities() {
		CapabilityDivingAttributesProvider.register();
		CapabilityOxygenProvider.register();
	}

}
