package meldexun.better_diving.capability.energy;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProviderSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CapabilityEnergyStorageProvider extends BasicCapabilityProviderSerializable<IEnergyStorage> {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(BetterDiving.MOD_ID, "energy_storage");

	public CapabilityEnergyStorageProvider(NonNullSupplier<IEnergyStorage> instanceSupplier) {
		super(CapabilityEnergy.ENERGY, instanceSupplier);
	}

}
