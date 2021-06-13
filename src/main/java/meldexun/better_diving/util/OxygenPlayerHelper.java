package meldexun.better_diving.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class OxygenPlayerHelper {

	public static int getOxygenRespectEquipment(PlayerEntity player) {
		int oxygenOfPlayer = Math.min(player.getAirSupply(), player.getMaxAirSupply());

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
		int oxygenCapacity = player.getMaxAirSupply();

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
		int amountReceived = 0;

		int i = MathHelper.clamp(amount, 0, player.getMaxAirSupply() - player.getAirSupply());
		player.setAirSupply(player.getAirSupply() + i);
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

		int i = MathHelper.clamp(amount, 0, player.getAirSupply() + 20);
		player.setAirSupply(player.getAirSupply() - i);
		amountExtracted += i;
		amount -= i;

		return amountExtracted;
	}

}
