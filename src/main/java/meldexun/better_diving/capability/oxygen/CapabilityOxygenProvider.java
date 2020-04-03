package meldexun.better_diving.capability.oxygen;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityOxygenProvider extends BasicCapabilityProvider<ICapabilityOxygen> {

	public static final ResourceLocation LOCATION_OXYGEN = new ResourceLocation(BetterDiving.MOD_ID, "oxygen");

	@CapabilityInject(ICapabilityOxygen.class)
	public static final Capability<ICapabilityOxygen> OXYGEN = null;

	public CapabilityOxygenProvider(Capability<ICapabilityOxygen> capability, ICapabilityOxygen instance) {
		super(capability, instance);
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(ICapabilityOxygen.class, new CapabilityOxygenStorage(), CapabilityOxygen::new);
	}

	public static CapabilityOxygenProvider createProvider(int oxygenCapacity, int oxygen) {
		return new CapabilityOxygenProvider(OXYGEN, new CapabilityOxygen(oxygenCapacity, oxygen));
	}

}
