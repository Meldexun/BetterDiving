package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.capability.energy.IEnergyStorageExtended;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemEnergyStorage extends ItemTooltip {

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
			((IEnergyStorageExtended) stack.getCapability(CapabilityEnergy.ENERGY, null)).setEnergy(0);
			items.add(stack);
		}
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
		if (flagIn.isAdvanced()) {
			tooltip.add(I18n.format("tooltip.energy_advanced", ItemEnergyStorage.getEnergyPercent(stack), ItemEnergyStorage.getEnergy(stack), this.capacity));
		} else {
			tooltip.add(I18n.format("tooltip.energy", ItemEnergyStorage.getEnergyPercent(stack)));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
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
			IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
			((IEnergyStorageExtended) ienergy).setEnergy(energy);
			return true;
		}
		return false;
	}

	public static int getEnergyPercent(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
			return ((IEnergyStorageExtended) ienergy).getEnergyPercent();
		}
		return 0;
	}

	public static int getEnergyCapacity(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
			return ienergy.getMaxEnergyStored();
		}
		return 0;
	}

	public static int receiveEnergy(ItemStack stack, int amount) {
		if (stack.getItem() instanceof ItemEnergyStorage && amount > 0) {
			IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
			return ienergy.receiveEnergy(amount, false);
		}
		return 0;
	}

	public static int extractEnergy(ItemStack stack, int amount) {
		if (stack.getItem() instanceof ItemEnergyStorage && amount > 0) {
			IEnergyStorage ienergy = stack.getCapability(CapabilityEnergy.ENERGY, null);
			return ienergy.extractEnergy(amount, false);
		}
		return 0;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getMaxReceive() {
		return this.maxReceive;
	}

	public int getMaxExtract() {
		return this.maxExtract;
	}

}
