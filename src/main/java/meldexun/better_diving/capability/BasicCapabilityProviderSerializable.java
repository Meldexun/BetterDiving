package meldexun.better_diving.capability;

import net.minecraft.nbt.INBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.NonNullSupplier;

public class BasicCapabilityProviderSerializable<C> extends BasicCapabilityProvider<C> implements ICapabilitySerializable<INBT> {

	public BasicCapabilityProviderSerializable(Capability<C> capability, NonNullSupplier<C> instanceSupplier) {
		super(capability, instanceSupplier);
	}

	@Override
	public INBT serializeNBT() {
		return this.capability.writeNBT(this.instance.orElse(null), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		this.capability.readNBT(this.instance.orElse(null), null, nbt);
	}

}
