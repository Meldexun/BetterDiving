package meldexun.better_diving.capability.oxygen.entity;

import meldexun.better_diving.api.capability.ICapabilityOxygen;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityOxygenStorage implements IStorage<ICapabilityOxygen> {

	@Override
	public INBT writeNBT(Capability<ICapabilityOxygen> capability, ICapabilityOxygen instance, Direction side) {
		return IntNBT.valueOf(instance.getOxygen());
	}

	@Override
	public void readNBT(Capability<ICapabilityOxygen> capability, ICapabilityOxygen instance, Direction side, INBT nbt) {
		if (nbt instanceof IntNBT) {
			instance.setOxygen(((IntNBT) nbt).getAsInt());
		}
	}

}
