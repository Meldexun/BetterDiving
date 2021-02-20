package meldexun.better_diving.capability.energy.item;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CapabilityEnergyStorageItemProvider extends BasicCapabilityProvider<IEnergyStorage> {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(BetterDiving.MOD_ID, "energy_storage");

	public CapabilityEnergyStorageItemProvider(NonNullSupplier<IEnergyStorage> instanceSupplier) {
		super(CapabilityEnergy.ENERGY, instanceSupplier);
	}

}
