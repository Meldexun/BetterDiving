package meldexun.better_diving.item;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CustomArmorMaterial implements IArmorMaterial {

	private final String name;
	private final BetterDivingConfig.ServerConfig.ArmorValues config;
	private final Ingredient ingredient;

	public CustomArmorMaterial(String name, BetterDivingConfig.ServerConfig.ArmorValues config) {
		this(name, config, Ingredient.EMPTY);
	}

	public CustomArmorMaterial(String name, BetterDivingConfig.ServerConfig.ArmorValues config, Ingredient ingredient) {
		this.name = BetterDiving.MOD_ID + ':' + name;
		this.config = config;
		this.ingredient = ingredient;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlotType var1) {
		return this.config.durability.get();
	}

	@Override
	public int getDefenseForSlot(EquipmentSlotType var1) {
		return this.config.protection.get().get(var1.getIndex());
	}

	@Override
	public int getEnchantmentValue() {
		return this.config.enchantability.get();
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ARMOR_EQUIP_GENERIC;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.ingredient;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return (float) (double) this.config.toughness.get();
	}

	@Override
	public float getKnockbackResistance() {
		return (float) (double) this.config.knockbackResistance.get();
	}

}
