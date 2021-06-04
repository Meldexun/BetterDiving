package meldexun.better_diving.capability.oxygen.entity.player;

import meldexun.better_diving.api.capability.ICapabilityOxygen;
import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class CapabilityOxygenPlayer implements ICapabilityOxygen {

	private final PlayerEntity player;
	private int oxygen;

	public CapabilityOxygenPlayer(PlayerEntity player) {
		this.player = player;
		this.setOxygen(this.getOxygenCapacity());
	}

	@Override
	public void setOxygen(int amount) {
		this.oxygen = MathHelper.clamp(amount, -20, this.getOxygenCapacity());
	}

	@Override
	public int getOxygen() {
		return this.oxygen;
	}

	@Override
	public int getOxygenCapacity() {
		int oxygenCap = BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenCapacity.get();
		ItemStack stack = this.player.getItemBySlot(EquipmentSlotType.HEAD);
		if (!stack.isEmpty()) {
			int respirationLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.RESPIRATION, stack);
			if (respirationLevel > 0) {
				oxygenCap += BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenCapacityRespiration.get() * respirationLevel;
			}
		}
		return oxygenCap;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygen() + 20);
		this.setOxygen(this.getOxygen() - amount);
		return amount;
	}

}
