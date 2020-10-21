package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.init.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemTool extends ItemTooltip {

	public ItemTool() {
		this.setMaxStackSize(1);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			((ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).setStackInSlot(0, new ItemStack(ModItems.BATTERY));
			items.add(stack);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (battery.getItem() instanceof ItemBattery) {
			IEnergyStorage ienergy = battery.getCapability(CapabilityEnergy.ENERGY, null);
			int percent = (int) (100.0D * ienergy.getEnergyStored() / ienergy.getMaxEnergyStored());
			if (flagIn.isAdvanced()) {
				tooltip.add(I18n.format("tooltip.energy_advanced", percent, ienergy.getEnergyStored(), ienergy.getMaxEnergyStored()));
			} else {
				tooltip.add(I18n.format("tooltip.energy", percent));
			}
		} else {
			tooltip.add(I18n.format("tooltip.no_battery"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public static boolean hasEnergy(ItemStack stack) {
		ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (battery.getItem() instanceof ItemBattery) {
			return ItemEnergyStorage.hasEnergy(battery);
		}
		return false;
	}

	public static int getEnergy(ItemStack stack) {
		ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (battery.getItem() instanceof ItemBattery) {
			return ItemEnergyStorage.getEnergy(battery);
		}
		return 0;
	}

	public static boolean setEnergy(ItemStack stack, int energy) {
		ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (battery.getItem() instanceof ItemEnergyStorage) {
			return ItemEnergyStorage.setEnergy(battery, energy);
		}
		return false;
	}

	public static int getEnergyPercent(ItemStack stack) {
		ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (battery.getItem() instanceof ItemEnergyStorage) {
			return ItemEnergyStorage.getEnergyPercent(stack);
		}
		return 0;
	}

	public static int getEnergyCapacity(ItemStack stack) {
		ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (battery.getItem() instanceof ItemBattery) {
			return ItemEnergyStorage.getEnergyCapacity(battery);
		}
		return 0;
	}

	public static int receiveEnergy(ItemStack stack, int amount) {
		if (amount > 0) {
			ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
			if (battery.getItem() instanceof ItemBattery) {
				return ItemEnergyStorage.receiveEnergy(battery, amount);
			}
		}
		return 0;
	}

	public static int extractEnergy(ItemStack stack, int amount) {
		if (amount > 0) {
			ItemStack battery = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
			if (battery.getItem() instanceof ItemBattery) {
				return ItemEnergyStorage.extractEnergy(battery, amount);
			}
		}
		return 0;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		IItemHandler iitemhandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		((ItemStackHandler) iitemhandler).setStackInSlot(0, new ItemStack(ModItems.BATTERY));
	}

}
