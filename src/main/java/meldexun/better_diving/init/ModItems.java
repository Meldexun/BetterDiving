package meldexun.better_diving.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.item.ItemBattery;
import meldexun.better_diving.item.ItemCreepvineSeedCluster;
import meldexun.better_diving.item.ItemDivingChest;
import meldexun.better_diving.item.ItemDivingFeet;
import meldexun.better_diving.item.ItemDivingHelm;
import meldexun.better_diving.item.ItemDivingLegs;
import meldexun.better_diving.item.ItemFish;
import meldexun.better_diving.item.ItemHabitatBuilder;
import meldexun.better_diving.item.ItemPowerCell;
import meldexun.better_diving.item.ItemSeaglide;
import meldexun.better_diving.item.ItemSeamoth;
import meldexun.better_diving.item.ItemTooltip;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(BetterDiving.MOD_ID)
public class ModItems {

	public static final ItemTooltip TITANIUM = null;
	public static final ItemTooltip COPPER_ORE = null;
	public static final ItemTooltip SILVER_ORE = null;
	public static final ItemTooltip LEAD = null;
	public static final ItemTooltip GOLD = null;

	public static final ItemTooltip TITANIUM_INGOT = null;
	public static final ItemTooltip WIRING_KIT = null;
	public static final ItemBattery BATTERY = null;
	public static final ItemPowerCell POWER_CELL = null;

	public static final ItemCreepvineSeedCluster CREEPVINE_SEED_CLUSTER = null;

	public static final ItemTooltip LUBRICANT = null;
	public static final ItemTooltip SILICONE_RUBBER = null;
	public static final ItemTooltip FIBER_MESH = null;

	public static final ItemDivingHelm DIVING_MASK = null;
	public static final ItemDivingChest STANDARD_O2_TANK = null;
	public static final ItemDivingLegs WETSUIT_LEGGINGS = null;
	public static final ItemDivingFeet FINS = null;

	public static final ItemDivingHelm REBREATHER = null;
	public static final ItemDivingChest HIGH_CAPACITY_O2_TANK = null;
	public static final ItemDivingLegs IMPROVED_WETSUIT_LEGGINGS = null;
	public static final ItemDivingFeet ULTRA_GLIDE_FINS = null;

	public static final ItemDivingHelm REINFORCED_DIVING_MASK = null;
	public static final ItemDivingChest REINFORCED_O2_TANK = null;
	public static final ItemDivingLegs REINFORCED_WETSUIT_LEGGINGS = null;
	public static final ItemDivingFeet REINFORCED_FINS = null;

	public static final ItemSeamoth SEAMOTH = null;

	public static final ItemFish PEEPER = null;
	public static final ItemFish PEEPER_COOKED = null;
	public static final ItemFish PEEPER_CURED = null;
	public static final ItemFish BLADDERFISH = null;
	public static final ItemFish BLADDERFISH_COOKED = null;
	public static final ItemFish BLADDERFISH_CURED = null;
	public static final ItemFish GARRYFISH = null;
	public static final ItemFish GARRYFISH_COOKED = null;
	public static final ItemFish GARRYFISH_CURED = null;
	public static final ItemFish HOLEFISH = null;
	public static final ItemFish HOLEFISH_COOKED = null;
	public static final ItemFish HOLEFISH_CURED = null;
	public static final ItemFish BOOMERANG = null;
	public static final ItemFish BOOMERANG_COOKED = null;
	public static final ItemFish BOOMERANG_CURED = null;

	public static final ItemHabitatBuilder HABITAT_BUILDER = null;
	public static final ItemSeaglide SEAGLIDE = null;

	private ModItems() {

	}

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	public static class ItemRegistrationHandler {

		public static final List<Item> ITEMS = new ArrayList<>();

		private ItemRegistrationHandler() {

		}

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			final Item[] items = {
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "titanium"),
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "copper_ore"),
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "silver_ore"),
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "lead"),
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "gold"),

					ItemRegistrationHandler.setItemName(new ItemTooltip(), "titanium_ingot"),
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "wiring_kit"),
					ItemRegistrationHandler.setItemName(new ItemBattery(1000000, 125, 1000), "battery"),
					ItemRegistrationHandler.setItemName(new ItemPowerCell(2000000, 125, 1000), "power_cell"),

					ItemRegistrationHandler.setItemName(new ItemCreepvineSeedCluster(), "creepvine_seed_cluster"),

					ItemRegistrationHandler.setItemName(new ItemTooltip(), "lubricant"),
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "silicone_rubber"),
					ItemRegistrationHandler.setItemName(new ItemTooltip(), "fiber_mesh"),

					ItemRegistrationHandler.setItemName(new ItemDivingHelm(ModMaterials.DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.basicDivingGear), "diving_mask"),
					ItemRegistrationHandler.setItemName(new ItemDivingChest(ModMaterials.DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.basicDivingGear), "standard_o2_tank"),
					ItemRegistrationHandler.setItemName(new ItemDivingLegs(ModMaterials.DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.basicDivingGear), "wetsuit_leggings"),
					ItemRegistrationHandler.setItemName(new ItemDivingFeet(ModMaterials.DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.basicDivingGear), "fins"),

					ItemRegistrationHandler.setItemName(new ItemDivingHelm(ModMaterials.IMPROVED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.improvedDivingGear), "rebreather"),
					ItemRegistrationHandler.setItemName(new ItemDivingChest(ModMaterials.IMPROVED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.improvedDivingGear), "high_capacity_o2_tank"),
					ItemRegistrationHandler.setItemName(new ItemDivingLegs(ModMaterials.IMPROVED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.improvedDivingGear), "improved_wetsuit_leggings"),
					ItemRegistrationHandler.setItemName(new ItemDivingFeet(ModMaterials.IMPROVED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.improvedDivingGear), "ultra_glide_fins"),

					ItemRegistrationHandler.setItemName(new ItemDivingHelm(ModMaterials.REINFORCED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.reinforcedDivingGear), "reinforced_diving_mask"),
					ItemRegistrationHandler.setItemName(new ItemDivingChest(ModMaterials.REINFORCED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.reinforcedDivingGear), "reinforced_o2_tank"),
					ItemRegistrationHandler.setItemName(new ItemDivingLegs(ModMaterials.REINFORCED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.reinforcedDivingGear), "reinforced_wetsuit_leggings"),
					ItemRegistrationHandler.setItemName(new ItemDivingFeet(ModMaterials.REINFORCED_DIVING_GEAR_ARMOR_MATERIAL, BetterDivingConfig.getInstance().divingGear.reinforcedDivingGear), "reinforced_fins"),

					ItemRegistrationHandler.setItemName(new ItemSeamoth(), "seamoth"),

					ItemRegistrationHandler.setItemName(new ItemFish(1, 0.5F), "peeper"),
					ItemRegistrationHandler.setItemName(new ItemFish(3, 0.5F), "peeper_cooked"),
					ItemRegistrationHandler.setItemName(new ItemFish(4, 0.5F), "peeper_cured"),
					ItemRegistrationHandler.setItemName(new ItemFish(1, 0.5F), "bladderfish"),
					ItemRegistrationHandler.setItemName(new ItemFish(3, 0.5F), "bladderfish_cooked"),
					ItemRegistrationHandler.setItemName(new ItemFish(4, 0.5F), "bladderfish_cured"),
					ItemRegistrationHandler.setItemName(new ItemFish(1, 0.5F), "garryfish"),
					ItemRegistrationHandler.setItemName(new ItemFish(3, 0.5F), "garryfish_cooked"),
					ItemRegistrationHandler.setItemName(new ItemFish(4, 0.5F), "garryfish_cured"),
					ItemRegistrationHandler.setItemName(new ItemFish(1, 0.5F), "holefish"),
					ItemRegistrationHandler.setItemName(new ItemFish(3, 0.5F), "holefish_cooked"),
					ItemRegistrationHandler.setItemName(new ItemFish(4, 0.5F), "holefish_cured"),
					ItemRegistrationHandler.setItemName(new ItemFish(1, 0.5F), "boomerang"),
					ItemRegistrationHandler.setItemName(new ItemFish(3, 0.5F), "boomerang_cooked"),
					ItemRegistrationHandler.setItemName(new ItemFish(4, 0.5F), "boomerang_cured"),

					ItemRegistrationHandler.setItemName(new ItemHabitatBuilder(), "habitat_builder"),
					ItemRegistrationHandler.setItemName(new ItemSeaglide(), "seaglide") };

			IForgeRegistry<Item> registry = event.getRegistry();

			for (Item item : items) {
				registry.register(item);
				ItemRegistrationHandler.ITEMS.add(item);
			}
		}

		private static Item setItemName(Item item, String name) {
			return ItemRegistrationHandler.setItemNameAndTab(item, name, BetterDiving.TAB_BETTER_DIVING);
		}

		private static Item setItemNameAndTab(Item item, String name, @Nullable CreativeTabs tab) {
			return item.setUnlocalizedName(name).setRegistryName(BetterDiving.MOD_ID, name).setCreativeTab(tab);
		}
	}

	public static void registerOreDictionaryEntries() {
		OreDictionary.registerOre("oreTitanium", new ItemStack(ModItems.TITANIUM));
		OreDictionary.registerOre("ingotTitaniun", new ItemStack(ModItems.TITANIUM_INGOT));
		OreDictionary.registerOre("oreCopper", new ItemStack(ModItems.COPPER_ORE));
		OreDictionary.registerOre("oreSilver", new ItemStack(ModItems.SILVER_ORE));
		OreDictionary.registerOre("oreLead", new ItemStack(ModItems.LEAD));
	}

	public static void registerFurnaceRecipes() {
		GameRegistry.addSmelting(ModItems.GOLD, new ItemStack(Items.GOLD_INGOT), 0.2F);
		GameRegistry.addSmelting(ModItems.PEEPER, new ItemStack(ModItems.PEEPER_COOKED), 0.1F);
		GameRegistry.addSmelting(ModItems.BLADDERFISH, new ItemStack(ModItems.BLADDERFISH_COOKED), 0.1F);
		GameRegistry.addSmelting(ModItems.GARRYFISH, new ItemStack(ModItems.GARRYFISH_COOKED), 0.1F);
		GameRegistry.addSmelting(ModItems.HOLEFISH, new ItemStack(ModItems.HOLEFISH_COOKED), 0.1F);
		GameRegistry.addSmelting(ModItems.BOOMERANG, new ItemStack(ModItems.BOOMERANG_COOKED), 0.1F);
	}

}
