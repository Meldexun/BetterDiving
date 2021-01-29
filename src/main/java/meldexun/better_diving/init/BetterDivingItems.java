package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.item.AbstractItemDivingGear;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BetterDivingItems {

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterDiving.MOD_ID);

	public static final RegistryObject<AbstractItemDivingGear> DIVING_MASK = ITEMS.register("diving_mask", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.HEAD, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> STANDARD_O2_TANK = ITEMS.register("standard_o2_tank", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.CHEST, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> WETSUIT_LEGGINGS = ITEMS.register("wetsuit_leggings", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.LEGS, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> FINS = ITEMS.register("fins", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.FEET, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));

	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_DIVING_MASK = ITEMS.register("rebreather", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.HEAD, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_STANDARD_O2_TANK = ITEMS.register("high_capacity_o2_tank", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.CHEST, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_WETSUIT_LEGGINGS = ITEMS.register("improved_wetsuit_leggings", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.LEGS, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_FINS = ITEMS.register("ultra_glide_fins", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.FEET, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));

	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_DIVING_MASK = ITEMS.register("reinforced_diving_mask", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.HEAD, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_STANDARD_O2_TANK = ITEMS.register("reinforced_o2_tank", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.CHEST, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_WETSUIT_LEGGINGS = ITEMS.register("reinforced_wetsuit_leggings", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.LEGS, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_FINS = ITEMS.register("reinforced_fins", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.FEET, new Item.Properties().group(BetterDivingItemGroups.BETTER_DIVING)));

	public static void registerItems() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
