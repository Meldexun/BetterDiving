package meldexun.better_diving.capability.oxygen.entity.player;

import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygen;
import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class CapabilityOxygenPlayer extends CapabilityOxygen {

	private final PlayerEntity player;

	public CapabilityOxygenPlayer(PlayerEntity player, int oxygenCapacity) {
		super(oxygenCapacity);
		this.player = player;
	}

	@Override
	public void setOxygen(int amount) {
		this.oxygen = MathHelper.clamp(amount, -20, this.getOxygenCapacity());
	}

	@Override
	public int getOxygenCapacity() {
		int oxygenCap = super.getOxygenCapacity();
		ItemStack stack = this.player.getItemStackFromSlot(EquipmentSlotType.HEAD);
		if (!stack.isEmpty()) {
			int respirationLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack);
			if (respirationLevel > 0) {
				oxygenCap += BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenCapacityRespiration.get() * respirationLevel;
			}
		}
		return oxygenCap;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygen() + 20);
		this.oxygen = this.oxygen - amount;
		return amount;
	}

}
