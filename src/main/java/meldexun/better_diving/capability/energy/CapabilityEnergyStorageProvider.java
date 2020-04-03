package meldexun.better_diving.capability.energy;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CapabilityEnergyStorageProvider extends BasicCapabilityProvider<IEnergyStorage> {

	public static final ResourceLocation LOCATION_ENERGY_STORAGE = new ResourceLocation(BetterDiving.MOD_ID, "energy_storage");

	public CapabilityEnergyStorageProvider(Capability<IEnergyStorage> capability, IEnergyStorage instance) {
		super(capability, instance);
	}

	public static CapabilityEnergyStorageProvider createProvider(int capacity, int maxReceive, int maxExtract, int energy) {
		return new CapabilityEnergyStorageProvider(CapabilityEnergy.ENERGY, new CapabilityEnergyStorage(capacity, maxReceive, maxExtract, energy));
	}

}
