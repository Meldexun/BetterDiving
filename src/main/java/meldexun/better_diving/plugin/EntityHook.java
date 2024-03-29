package meldexun.better_diving.plugin;

import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

public class EntityHook {

	public static boolean canBeInFluid(Entity entity, ITag<Fluid> tag) {
		return !(entity.getVehicle() instanceof EntitySeamoth);
	}

	public static int getMaxAirSupply(PlayerEntity player) {
		int oxygenCapacity = BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenCapacity.get();
		ItemStack stack = player.inventory != null ? player.getItemBySlot(EquipmentSlotType.HEAD) : ItemStack.EMPTY;

		if (!stack.isEmpty()) {
			int respirationLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.RESPIRATION, stack);
			if (respirationLevel > 0) {
				oxygenCapacity += BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenCapacityRespiration.get() * respirationLevel;
			}
		}

		return oxygenCapacity;
	}

}
