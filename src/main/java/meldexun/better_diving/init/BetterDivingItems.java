package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.item.AbstractItemDivingGear;
import meldexun.better_diving.item.ItemPowerCell;
import meldexun.better_diving.item.ItemSeamoth;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BetterDivingItems {

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterDiving.MOD_ID);

	public static final RegistryObject<Item> TITANIUM_CHUNK = ITEMS.register("titanium_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> COPPER_CHUNK = ITEMS.register("copper_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> SILVER_CHUNK = ITEMS.register("silver_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> LEAD_CHUNK = ITEMS.register("lead_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> LITHIUM_CHUNK = ITEMS.register("lithium_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));

	public static final RegistryObject<Item> COAL_CHUNK = ITEMS.register("coal_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> IRON_CHUNK = ITEMS.register("iron_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> GOLD_CHUNK = ITEMS.register("gold_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> REDSTONE_CHUNK = ITEMS.register("redstone_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> LAPIS_CHUNK = ITEMS.register("lapis_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> EMERALD_CHUNK = ITEMS.register("emerald_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> DIAMOND_CHUNK = ITEMS.register("diamond_chunk", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));

	public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
	public static final RegistryObject<Item> LITHIUM_INGOT = ITEMS.register("lithium_ingot", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));

	public static final RegistryObject<Item> FIBER_MESH = ITEMS.register("fiber_mesh", () -> new Item(new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));

	public static final RegistryObject<ItemPowerCell> POWER_CELL = ITEMS.register("power_cell", () -> new ItemPowerCell(BetterDivingConfig.SERVER_CONFIG.powerCell));

	public static final RegistryObject<ItemSeamoth> SEAMOTH = ITEMS.register("seamoth", ItemSeamoth::new);

	public static final RegistryObject<AbstractItemDivingGear> DIVING_MASK = ITEMS.register("diving_mask", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.HEAD));
	public static final RegistryObject<AbstractItemDivingGear> STANDARD_O2_TANK = ITEMS.register("standard_o2_tank", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.CHEST));
	public static final RegistryObject<AbstractItemDivingGear> WETSUIT_LEGGINGS = ITEMS.register("wetsuit_leggings", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.LEGS));
	public static final RegistryObject<AbstractItemDivingGear> FINS = ITEMS.register("fins", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.DIVING_GEAR, EquipmentSlotType.FEET));

	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_DIVING_MASK = ITEMS.register("rebreather", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.HEAD));
	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_STANDARD_O2_TANK = ITEMS.register("high_capacity_o2_tank", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.CHEST));
	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_WETSUIT_LEGGINGS = ITEMS.register("improved_wetsuit_leggings", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.LEGS));
	public static final RegistryObject<AbstractItemDivingGear> IMPROVED_FINS = ITEMS.register("ultra_glide_fins", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.IMPROVED_DIVING_GEAR, EquipmentSlotType.FEET));

	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_DIVING_MASK = ITEMS.register("reinforced_diving_mask", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.HEAD));
	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_STANDARD_O2_TANK = ITEMS.register("reinforced_o2_tank", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.CHEST));
	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_WETSUIT_LEGGINGS = ITEMS.register("reinforced_wetsuit_leggings", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.LEGS));
	public static final RegistryObject<AbstractItemDivingGear> REINFORCED_FINS = ITEMS.register("reinforced_fins", () -> new AbstractItemDivingGear(BetterDivingMaterials.ArmorMaterials.REINFORCED_DIVING_GEAR, EquipmentSlotType.FEET));

	public static void registerItems() {
		for (RegistryObject<Block> block : BetterDivingBlocks.BLOCKS.getEntries()) {
			ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(BetterDivingItemGroups.BETTER_DIVING)));
		}
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
