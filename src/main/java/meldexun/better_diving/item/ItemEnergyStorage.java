package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.api.capability.IEnergyStorageExtended;
import meldexun.better_diving.capability.energy.item.CapabilityEnergyStorageItem;
import meldexun.better_diving.capability.energy.item.CapabilityEnergyStorageItemProvider;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.init.BetterDivingItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemEnergyStorage extends Item {

	protected final BetterDivingConfig.ServerConfig.EnergyStorageItem config;

	public ItemEnergyStorage(BetterDivingConfig.ServerConfig.EnergyStorageItem config) {
		super(new Item.Properties().stacksTo(1).tab(BetterDivingItemGroups.BETTER_DIVING));
		this.config = config;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new CapabilityEnergyStorageItemProvider(() -> new CapabilityEnergyStorageItem(stack, this.getCapacity(), this.getMaxReceive(), this.getMaxExtract(), this.getEnergy()));
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
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
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		int energy = MathHelper.ceil(getEnergyPercent(stack) * 100.0D);
		if (flagIn.isAdvanced()) {
			tooltip.add(new StringTextComponent(TextFormatting.GRAY + String.format("Energy %d%% (%d/%d)", energy, getEnergy(stack), getEnergyCapacity(stack))));
		} else {
			tooltip.add(new StringTextComponent(TextFormatting.GRAY + String.format("Energy %d%%", energy)));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
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

	public static double getEnergyPercent(ItemStack stack) {
		if (stack.getItem() instanceof ItemEnergyStorage) {
			LazyOptional<IEnergyStorage> optionalOxygenCap = stack.getCapability(CapabilityEnergy.ENERGY);
			if (!optionalOxygenCap.isPresent()) {
				return 0.0D;
			}
			IEnergyStorage energyCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
			return ((IEnergyStorageExtended) energyCap).getEnergyPercent();
		}
		return 0.0D;
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
		return this.config.capacity.get();
	}

	public int getMaxReceive() {
		return this.config.maxReceive.get();
	}

	public int getMaxExtract() {
		return this.config.maxExtract.get();
	}

	public int getEnergy() {
		return this.config.energy.get();
	}

}
