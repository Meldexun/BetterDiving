package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.util.DivingGearHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class PlayerMiningEventHandler {

	@SubscribeEvent
	public static void onPlayerBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
		if (!BetterDivingConfig.SERVER_CONFIG.breakSpeedChanges.get()) {
			return;
		}

		PlayerEntity player = event.getPlayer();

		if (player.isInWater()) {
			float multiplier = 1.0F + (float) DivingGearHelper.getMiningspeedBonus(player);

			if (player.areEyesInFluid(FluidTags.WATER)) {
				ItemStack head = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
				int aquaAffinityLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.AQUA_AFFINITY, head);
				if (aquaAffinityLevel > 0) {
					multiplier *= 1.0F + BetterDivingConfig.SERVER_CONFIG.breakSpeedAquaAffinity.get() * aquaAffinityLevel;
				} else {
					multiplier *= 5.0F;
				}
			}

			if (!player.isOnGround()) {
				multiplier *= 5.0F;
			}

			event.setNewSpeed(event.getNewSpeed() * multiplier);
		}
	}

}
