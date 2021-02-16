package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.capability.energy.CapabilityEnergyStorage;
import meldexun.better_diving.capability.energy.CapabilityEnergyStorageProvider;
import meldexun.better_diving.capability.energy.IEnergyStorageExtended;
import meldexun.better_diving.init.BetterDivingItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemEnergyStorage extends Item {

	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public ItemEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		super(new Item.Properties().maxStackSize(1).group(BetterDivingItemGroups.BETTER_DIVING));
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new CapabilityEnergyStorageProvider(() -> new CapabilityEnergyStorage(this.capacity, this.maxReceive, this.maxExtract, this.capacity));
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			items.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(c -> {
				((IEnergyStorageExtended) c).setEnergy(0);
			});
			items.add(stack);
		}
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
		if (!optionalOxygenCap.isPresent()) {
			return 1.0D;
		}
		IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return 1.0D - ((double) energyCap.getEnergyStored() / (double) energyCap.getMaxEnergyStored());
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(TextFormatting.GRAY + String.format("Energy %d%%", ItemEnergyStorage.getEnergyPercent(stack))));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public static boolean hasEnergy(ItemStack stack) {
		return ItemEnergyStorage.getEnergy(stack) > 0;
	}

	public static int getEnergy(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
			if (!optionalOxygenCap.isPresent()) {
				return 0;
			}
			IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
			return energyCap.getEnergyStored();
		}
		return 0;
	}

	public static boolean setEnergy(ItemStack stack, int energy) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
			if (!optionalOxygenCap.isPresent()) {
				return false;
			}
			IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
			((IEnergyStorageExtended) energyCap).setEnergy(energy);
			return true;
		}
		return false;
	}

	public static int getEnergyPercent(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
			if (!optionalOxygenCap.isPresent()) {
				return 0;
			}
			IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
			return ((IEnergyStorageExtended) energyCap).getEnergyPercent();
		}
		return 0;
	}

	public static int getEnergyCapacity(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
			if (!optionalOxygenCap.isPresent()) {
				return 0;
			}
			IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
			return energyCap.getMaxEnergyStored();
		}
		return 0;
	}

	public static int receiveEnergy(ItemStack stack, int amount) {
		if (stack.getItem() instanceof ItemEnergyStorage && amount > 0) {
			LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
			if (!optionalOxygenCap.isPresent()) {
				return 0;
			}
			IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
			return energyCap.receiveEnergy(amount, false);
		}
		return 0;
	}

	public static int extractEnergy(ItemStack stack, int amount) {
		if (stack.getItem() instanceof ItemEnergyStorage && amount > 0) {
			LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
			if (!optionalOxygenCap.isPresent()) {
				return 0;
			}
			IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
			return energyCap.extractEnergy(amount, false);
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
