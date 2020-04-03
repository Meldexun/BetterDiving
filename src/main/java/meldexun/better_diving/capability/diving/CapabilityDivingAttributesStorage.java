package meldexun.better_diving.capability.diving;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityDivingAttributesStorage implements IStorage<ICapabilityDivingAttributes> {

	@Override
	public NBTBase writeNBT(Capability<ICapabilityDivingAttributes> capability, ICapabilityDivingAttributes instance, EnumFacing side) {
		return new NBTTagInt(instance.getOxygen());
	}

	@Override
	public void readNBT(Capability<ICapabilityDivingAttributes> capability, ICapabilityDivingAttributes instance, EnumFacing side, NBTBase nbt) {
		if (nbt instanceof NBTTagInt) {
			instance.setOxygen(((NBTTagInt) nbt).getInt());
		}
	}

}
