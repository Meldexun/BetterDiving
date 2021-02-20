package meldexun.better_diving.capability.energy.item;

import meldexun.better_diving.api.capability.IEnergyStorageExtended;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.Constants;

public class CapabilityEnergyStorageItem implements IEnergyStorageExtended {

	private final ItemStack stack;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public CapabilityEnergyStorageItem(ItemStack stack, int capacity, int maxReceive, int maxExtract, int energy) {
		this.stack = stack;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		CompoundNBT nbt = this.stack.getTag();
		if (nbt == null || !nbt.contains(CapabilityEnergyStorageItemProvider.REGISTRY_NAME.toString(), Constants.NBT.TAG_INT)) {
			this.setEnergy(energy);
		}
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!this.canReceive() || maxReceive <= 0) {
			return 0;
		}
		int energyReceived = Math.min(maxReceive, Math.min(this.getMaxEnergyStored() - this.getEnergyStored(), this.maxReceive));
		if (!simulate) {
			this.setEnergy(this.getEnergyStored() + energyReceived);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!this.canExtract() || maxExtract <= 0) {
			return 0;
		}
		int energyExtracted = Math.min(maxExtract, Math.min(this.getEnergyStored(), this.maxExtract));
		if (!simulate) {
			this.setEnergy(this.getEnergyStored() - energyExtracted);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {
		CompoundNBT nbt = this.stack.getTag();
		return nbt != null ? nbt.getInt(CapabilityEnergyStorageItemProvider.REGISTRY_NAME.toString()) : 0;
	}

	@Override
	public int getMaxEnergyStored() {
		return this.capacity;
	}

	@Override
	public boolean canExtract() {
		return this.maxExtract > 0;
	}

	@Override
	public boolean canReceive() {
		return this.maxReceive > 0;
	}

	@Override
	public void setEnergy(int energy) {
		CompoundNBT nbt = this.stack.getOrCreateTag();
		nbt.putInt(CapabilityEnergyStorageItemProvider.REGISTRY_NAME.toString(), MathHelper.clamp(energy, 0, this.getMaxEnergyStored()));
	}

	@Override
	public double getEnergyPercent() {
		return (double) this.getEnergyStored() / (double) this.getMaxEnergyStored();
	}

}
