package meldexun.better_diving.util;

import meldexun.better_diving.api.capability.ICapabilityOxygen;
import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygenProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.LazyOptional;

public class OxygenPlayerHelper {

	public static int getOxygenRespectEquipment(PlayerEntity player) {
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = player.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		int oxygenOfPlayer = oxygenCap.getOxygen();

		Entity ridingEntity = player.getVehicle();
		if (ridingEntity != null) {
			oxygenOfPlayer += OxygenEntityHelper.getOxygen(ridingEntity, player);
		}

		for (EquipmentSlotType slot : EquipmentSlotType.values()) {
			ItemStack stack = player.getItemBySlot(slot);
			oxygenOfPlayer += OxygenItemHelper.getOxygen(stack, player, slot);
		}

		return oxygenOfPlayer;
	}

	public static int getOxygenCapacityRespectEquipment(PlayerEntity player) {
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = player.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		int oxygenCapacity = oxygenCap.getOxygenCapacity();

		Entity ridingEntity = player.getVehicle();
		if (ridingEntity != null) {
			oxygenCapacity += OxygenEntityHelper.getOxygenCapacity(ridingEntity, player);
		}

		for (EquipmentSlotType slot : EquipmentSlotType.values()) {
			ItemStack stack = player.getItemBySlot(slot);
			oxygenCapacity += OxygenItemHelper.getOxygenCapacity(stack, player, slot);
		}

		return oxygenCapacity;
	}

	public static double getOxygenRespectEquipmentInPercent(PlayerEntity player) {
		return (double) Math.max(getOxygenRespectEquipment(player), 0) / (double) getOxygenCapacityRespectEquipment(player);
	}

	public static int receiveOxygenRespectEquipment(PlayerEntity player, int amount) {
		amount = MathHelper.clamp(amount, 0, getOxygenCapacityRespectEquipment(player) - getOxygenRespectEquipment(player));
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = player.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		int amountReceived = 0;

		int i = oxygenCap.receiveOxygen(amount);
		amountReceived += i;
		amount -= i;

		for (EquipmentSlotType slot : EquipmentSlotType.values()) {
			ItemStack stack = player.getItemBySlot(slot);
			int j = OxygenItemHelper.receiveOxygen(stack, amount, player, slot);
			amountReceived += j;
			amount -= j;
		}

		Entity ridingEntity = player.getVehicle();
		if (ridingEntity != null) {
			int j = OxygenEntityHelper.receiveOxygen(ridingEntity, player, amount);
			amountReceived += j;
			amount -= j;
		}

		return amountReceived;
	}

	public static int extractOxygenRespectEquipment(PlayerEntity player, int amount) {
		amount = MathHelper.clamp(amount, 0, getOxygenRespectEquipment(player) + 20);
		LazyOptional<ICapabilityOxygen> optionalOxygenCap = player.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (!optionalOxygenCap.isPresent()) {
			return 0;
		}
		ICapabilityOxygen oxygenCap = optionalOxygenCap.orElseThrow(NullPointerException::new);
		int amountExtracted = 0;

		Entity ridingEntity = player.getVehicle();
		if (ridingEntity != null) {
			int j = OxygenEntityHelper.extractOxygen(ridingEntity, player, amount);
			amountExtracted += j;
			amount -= j;
		}

		for (EquipmentSlotType slot : EquipmentSlotType.values()) {
			ItemStack stack = player.getItemBySlot(slot);
			int j = OxygenItemHelper.extractOxygen(stack, amount, player, slot);
			amountExtracted += j;
			amount -= j;
		}

		int i = oxygenCap.extractOxygen(amount);
		amountExtracted += i;
		amount -= i;

		return amountExtracted;
	}

}
