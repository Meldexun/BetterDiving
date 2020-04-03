package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.capability.energy.CapabilityEnergyStorage;
import meldexun.better_diving.capability.energy.CapabilityEnergyStorageProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemEnergyStorage extends Item {

	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public ItemEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		this.setMaxStackSize(1);
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			((CapabilityEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null)).setEnergy(0);
			items.add(stack);
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return CapabilityEnergyStorageProvider.createProvider(this.capacity, this.maxReceive, this.maxExtract, this.capacity);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		IEnergyStorage s = stack.getCapability(CapabilityEnergy.ENERGY, null);
		return 1.0D - ((double) s.getEnergyStored() / (double) s.getMaxEnergyStored());
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
		int percent = (int) (100.0D * (double) ienergy.getEnergyStored() / (double) ienergy.getMaxEnergyStored());
		int energy = (int) ((double) ienergy.getEnergyStored() / 100.0D);
		int capacity = (int) ((double) ienergy.getMaxEnergyStored() / 100.0D);
		tooltip.add("Energy: " + percent + " % (" + energy + "/" + capacity + ")");
		tooltip.add(I18n.format(this.getUnlocalizedName() + ".tooltip"));
	}

	public static boolean hasEnergy(ItemStack stack) {
		return ItemEnergyStorage.getEnergy(stack) > 0;
	}

	public static int getEnergy(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
			return ienergy.getEnergyStored();
		}
		return 0;
	}

	public static boolean setEnergy(ItemStack stack, int energy) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			CapabilityEnergyStorage ienergy = (CapabilityEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);
			ienergy.setEnergy(energy);
			return true;
		}
		return false;
	}

	public static int getEnergyCapacity(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
			return ienergy.getMaxEnergyStored();
		}
		return 0;
	}

	public static int receiveEnergy(ItemStack stack, int amount) {
		if (amount > 0) {
			if (stack.getItem() instanceof ItemEnergyStorage) {
				IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
				return ienergy.receiveEnergy(amount, false);
			}
		}
		return 0;
	}

	public static int extractEnergy(ItemStack stack, int amount) {
		if (amount > 0) {
			if (stack.getItem() instanceof ItemEnergyStorage) {
				IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
				return ienergy.extractEnergy(amount, false);
			}
		}
		return 0;
	}

	@Override
	public NBTTagCompound getNBTShareTag(ItemStack stack) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("energy", ItemEnergyStorage.getEnergy(stack));
		return nbt;
	}

}
