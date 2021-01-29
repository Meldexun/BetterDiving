package meldexun.better_diving.util;

import meldexun.better_diving.api.capability.ICapabilityOxygenItem;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItemProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class OxygenItemHelper {

	/**
	 * Returns the amount of oxygen this item currently holds.
	 */
	public static int getOxygen(ItemStack stack) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.getOxygen();
	}

	/**
	 * Returns the amount of oxygen this item can hold.
	 */
	public static int getOxygenCapacity(ItemStack stack) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.getOxygenCapacity();
	}

	/**
	 * Returns the amount of oxygen that was received.
	 */
	public static int receiveOxygen(ItemStack stack, int amount) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.receiveOxygen(amount);
	}

	/**
	 * Returns the amount of oxygen that was extracted.
	 */
	public static int extractOxygen(ItemStack stack, int amount) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		return oxygenCap.extractOxygen(amount);
	}

	/**
	 * This should return 0 when this item is not in a valid slot!<br>
	 * Returns the amount of oxygen this item currently holds.
	 */
	public static int getOxygen(ItemStack stack, PlayerEntity player, EquipmentSlotType slot) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		if (!oxygenCap.canBeUsed(player)) {
			return 0;
		}
		if (oxygenCap.needsDivingMask() && !DivingGearHelper.isWearingDivingMask(player)) {
			return 0;
		}
		if (!oxygenCap.isValidSlot(slot)) {
			return 0;
		}
		return oxygenCap.getOxygen();
	}

	/**
	 * This should return 0 when this item is not in a valid slot!<br>
	 * Returns the amount of oxygen this item can hold.
	 */
	public static int getOxygenCapacity(ItemStack stack, PlayerEntity player, EquipmentSlotType slot) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		if (!oxygenCap.canBeUsed(player)) {
			return 0;
		}
		if (oxygenCap.needsDivingMask() && !DivingGearHelper.isWearingDivingMask(player)) {
			return 0;
		}
		if (!oxygenCap.isValidSlot(slot)) {
			return 0;
		}
		return oxygenCap.getOxygenCapacity();
	}

	/**
	 * This should return 0 when this item is not in a valid slot!<br>
	 * Returns the amount of oxygen that was received.
	 */
	public static int receiveOxygen(ItemStack stack, int amount, PlayerEntity player, EquipmentSlotType slot) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		if (!oxygenCap.canBeUsed(player)) {
			return 0;
		}
		if (oxygenCap.needsDivingMask() && !DivingGearHelper.isWearingDivingMask(player)) {
			return 0;
		}
		if (!oxygenCap.isValidSlot(slot)) {
			return 0;
		}
		return oxygenCap.receiveOxygen(amount);
	}

	/**
	 * This should return 0 when this item is not in a valid slot!<br>
	 * Returns the amount of oxygen that was extracted.
	 */
	public static int extractOxygen(ItemStack stack, int amount, PlayerEntity player, EquipmentSlotType slot) {
		LazyOptional<ICapabilityOxygenItem> optionalOxygenCap = stack.getCapability(CapabilityOxygenItemProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygenItem oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		if (!oxygenCap.canBeUsed(player)) {
			return 0;
		}
		if (oxygenCap.needsDivingMask() && !DivingGearHelper.isWearingDivingMask(player)) {
			return 0;
		}
		if (!oxygenCap.isValidSlot(slot)) {
			return 0;
		}
		return oxygenCap.extractOxygen(amount);
	}

}
