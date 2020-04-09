package meldexun.better_diving.capability.item.oxygen;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityOxygenStorage implements IStorage<ICapabilityOxygen> {

	@Override
	public NBTBase writeNBT(Capability<ICapabilityOxygen> capability, ICapabilityOxygen instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<ICapabilityOxygen> capability, ICapabilityOxygen instance, EnumFacing side, NBTBase nbt) {

	}

}
