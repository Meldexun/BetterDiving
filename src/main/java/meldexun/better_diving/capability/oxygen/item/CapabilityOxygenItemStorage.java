package meldexun.better_diving.capability.oxygen.item;

import meldexun.better_diving.api.capability.ICapabilityOxygenItem;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityOxygenItemStorage implements IStorage<ICapabilityOxygenItem> {

	@Override
	public INBT writeNBT(Capability<ICapabilityOxygenItem> capability, ICapabilityOxygenItem instance, Direction side) {
		return null;
	}

	@Override
	public void readNBT(Capability<ICapabilityOxygenItem> capability, ICapabilityOxygenItem instance, Direction side, INBT nbt) {

	}

}
