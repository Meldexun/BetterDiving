package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.config.ArmorConfig;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class ModMaterials {

	public static final ArmorMaterial DIVING_GEAR_ARMOR_MATERIAL = addArmorMaterial("diving_gear", BetterDivingConfig.DIVING_GEAR.basicDivingGear.armorValues, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
	public static final ArmorMaterial IMPROVED_DIVING_GEAR_ARMOR_MATERIAL = addArmorMaterial("improved_diving_gear", BetterDivingConfig.DIVING_GEAR.improvedDivingGear.armorValues, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
	public static final ArmorMaterial REINFORCED_DIVING_GEAR_ARMOR_MATERIAL = addArmorMaterial("reinforced_diving_gear", BetterDivingConfig.DIVING_GEAR.reinforcedDivingGear.armorValues, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);

	public static ArmorMaterial addArmorMaterial(String name, ArmorConfig config, SoundEvent soundOnEquip) {
		return addArmorMaterial(name, config.durability, config.protection, config.enchantability, soundOnEquip, (float) config.toughness);
	}

	public static ArmorMaterial addArmorMaterial(String name, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addArmorMaterial(name, BetterDiving.MOD_ID + ":" + name, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
	}

}
