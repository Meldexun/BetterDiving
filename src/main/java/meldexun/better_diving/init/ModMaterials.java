package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.config.ArmorConfig;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class ModMaterials {

	private ModMaterials() {

	}

	public static final ArmorMaterial DIVING_GEAR_ARMOR_MATERIAL = ModMaterials.addArmorMaterial("diving_gear", BetterDivingConfig.getInstance().divingGear.basicDivingGear.armorValues, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
	public static final ArmorMaterial IMPROVED_DIVING_GEAR_ARMOR_MATERIAL = ModMaterials.addArmorMaterial("improved_diving_gear", BetterDivingConfig.getInstance().divingGear.improvedDivingGear.armorValues, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
	public static final ArmorMaterial REINFORCED_DIVING_GEAR_ARMOR_MATERIAL = ModMaterials.addArmorMaterial("reinforced_diving_gear", BetterDivingConfig.getInstance().divingGear.reinforcedDivingGear.armorValues, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);

	public static ArmorMaterial addArmorMaterial(String name, ArmorConfig config, SoundEvent soundOnEquip) {
		return ModMaterials.addArmorMaterial(name, config.durability, config.protection, config.enchantability, soundOnEquip, config.toughness);
	}

	public static ArmorMaterial addArmorMaterial(String name, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addArmorMaterial(name, BetterDiving.MOD_ID + ":" + name, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
	}

}
