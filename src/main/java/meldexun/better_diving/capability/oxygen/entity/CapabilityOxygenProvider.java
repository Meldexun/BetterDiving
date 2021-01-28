package meldexun.better_diving.capability.oxygen.entity;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.api.capability.ICapabilityOxygen;
import meldexun.better_diving.capability.BasicCapabilityProviderSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.NonNullSupplier;

public class CapabilityOxygenProvider extends BasicCapabilityProviderSerializable<ICapabilityOxygen> {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(BetterDiving.MOD_ID, "oxygen");

	@CapabilityInject(ICapabilityOxygen.class)
	public static final Capability<ICapabilityOxygen> OXYGEN = null;

	public CapabilityOxygenProvider(NonNullSupplier<ICapabilityOxygen> supplier) {
		super(OXYGEN, supplier);
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(ICapabilityOxygen.class, new CapabilityOxygenStorage(), () -> new CapabilityOxygen(0));
	}

}
