package meldexun.better_diving.capability.item.energy;

import meldexun.better_diving.capability.energy.CapabilityEnergyStorageProvider;
import meldexun.better_diving.capability.energy.IEnergyStorageExtended;
import meldexun.better_diving.item.ItemEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class CapabilityEnergyStorageItem implements IEnergyStorageExtended {

	private final ItemStack stack;

	public CapabilityEnergyStorageItem() {
		this.stack = ItemStack.EMPTY;
	}

	public CapabilityEnergyStorageItem(ItemStack stack) {
		this.stack = stack;
		if (!this.stack.hasTagCompound()) {
			this.stack.setTagCompound(new NBTTagCompound());
		}
		if (!this.stack.getTagCompound().hasKey(CapabilityEnergyStorageProvider.LOCATION_ENERGY_STORAGE.toString())) {
			this.setEnergy(this.getMaxEnergyStored());
		}
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!this.canReceive()) {
			return 0;
		}

		int energyReceived = Math.min(this.getMaxEnergyStored() - this.getEnergyStored(), Math.min(this.getMaxReceive(), maxReceive));
		if (!simulate) {
			this.setEnergy(this.getEnergyStored() + energyReceived);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!this.canExtract()) {
			return 0;
		}

		int energyExtracted = Math.min(this.getEnergyStored(), Math.min(this.getMaxExtract(), maxExtract));
		if (!simulate) {
			this.setEnergy(this.getEnergyStored() - energyExtracted);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {
		if (this.stack.hasTagCompound()) {
			return this.stack.getTagCompound().getInteger(CapabilityEnergyStorageProvider.LOCATION_ENERGY_STORAGE.toString());
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		if (this.stack.getItem() instanceof ItemEnergyStorage) {
			return ((ItemEnergyStorage) this.stack.getItem()).getCapacity();
		}
		return 0;
	}

	@Override
	public boolean canExtract() {
		return this.getMaxExtract() > 0;
	}

	@Override
	public boolean canReceive() {
		return this.getMaxReceive() > 0;
	}

	@Override
	public void setEnergy(int energy) {
		if (this.stack.hasTagCompound()) {
			this.stack.getTagCompound().setInteger(CapabilityEnergyStorageProvider.LOCATION_ENERGY_STORAGE.toString(), MathHelper.clamp(energy, 0, this.getMaxEnergyStored()));
		}
	}

	@Override
	public int getEnergyPercent() {
		return (int) (100.0D * this.getEnergyStored() / this.getMaxEnergyStored());
	}

	public int getMaxReceive() {
		if (this.stack.getItem() instanceof ItemEnergyStorage) {
			return ((ItemEnergyStorage) this.stack.getItem()).getMaxReceive();
		}
		return 0;
	}

	public int getMaxExtract() {
		if (this.stack.getItem() instanceof ItemEnergyStorage) {
			return ((ItemEnergyStorage) this.stack.getItem()).getMaxExtract();
		}
		return 0;
	}

}
