package meldexun.better_diving.capability.item.energy;

import meldexun.better_diving.capability.BasicCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CapabilityEnergyStorageItemProvider extends BasicCapabilityProvider<IEnergyStorage> {

	public CapabilityEnergyStorageItemProvider(Capability<IEnergyStorage> capability, IEnergyStorage instance) {
		super(capability, instance);
	}

	public static CapabilityEnergyStorageItemProvider createProvider(ItemStack stack) {
		return new CapabilityEnergyStorageItemProvider(CapabilityEnergy.ENERGY, new CapabilityEnergyStorageItem(stack));
	}

}
