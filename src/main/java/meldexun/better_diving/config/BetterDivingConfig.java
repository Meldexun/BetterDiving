package meldexun.better_diving.config;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;

public class BetterDivingConfig {

	public static final ClientConfig CLIENT_CONFIG;
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ServerConfig SERVER_CONFIG;
	public static final ForgeConfigSpec SERVER_SPEC;
	static {
		final Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		final Pair<ServerConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		CLIENT_CONFIG = clientSpecPair.getLeft();
		CLIENT_SPEC = clientSpecPair.getRight();
		SERVER_CONFIG = serverSpecPair.getLeft();
		SERVER_SPEC = serverSpecPair.getRight();
	}

	public static boolean oxygenChanges() {
		return SERVER_CONFIG.oxygenChanges.get();
	}

	public static class ServerConfig {

		public final ForgeConfigSpec.BooleanValue breakSpeedChanges;
		public final ForgeConfigSpec.BooleanValue movementChanges;
		public final ForgeConfigSpec.BooleanValue oxygenChanges;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> oxygenProviderEntities;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> divingMaskProviderItems;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> oxygenProviderItems;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> miningspeedProviderItems;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> swimspeedProviderItems;

		public final EnergyStorageItem powerCell;

		public final ArmorValues divingGear;
		public final ArmorValues improvedDivingGear;
		public final ArmorValues reinforcedDivingGear;

		public final Mining mining;
		public final Movement movement;
		public final Ores ores;
		public final Oxygen oxygen;
		public final Seamoth seamoth;
		public final UnderwaterVisuals underwaterVisuals;

		public ServerConfig(ForgeConfigSpec.Builder builder) {
			this.breakSpeedChanges = builder.comment("").define("breakSpeedChanges", true);
			this.movementChanges = builder.comment("").define("movementChanges", true);
			this.oxygenChanges = builder.comment("").define("oxygenChanges", true);

			this.oxygenProviderEntities = builder.comment("modid:entity, oxygenCapacity").defineList("oxygenProviderEntities", Lists.newArrayList(), o -> true);
			this.divingMaskProviderItems = builder.comment("modid:item, maxDivingDepth").defineList("divingMaskProviderItems", Lists.newArrayList("better_diving:diving_mask, 40", "better_diving:rebreather, 60", "better_diving:reinforced_diving_mask, 40"), o -> true);
			this.oxygenProviderItems = builder.comment("modid:item, mainhand, offhand, feet, legs, chest, head, oxygenCapacity, needsDivingMask").defineList("oxygenProviderItems",
					Lists.newArrayList("better_diving:standard_o2_tank, false, false, false, false, true, false, 600, true", "better_diving:high_capacity_o2_tank, false, false, false, false, true, false, 1800, true", "better_diving:reinforced_o2_tank, false, false, false, false, true, false, 600, true"), o -> true);
			this.miningspeedProviderItems = builder.comment("modid:item, mainhand, offhand, feet, legs, chest, head, miningspeedBonus").defineList("miningspeedProviderItems",
					Lists.newArrayList("better_diving:wetsuit_leggings, false, false, false, true, false, false, 0.15", "better_diving:improved_wetsuit_leggings, false, false, false, true, false, false, 0.3", "better_diving:reinforced_wetsuit_leggings, false, false, false, true, false, false, 0.15"), o -> true);
			this.swimspeedProviderItems = builder.comment("modid:item, mainhand, offhand, feet, legs, chest, head, swimspeedBonus").defineList("swimspeedProviderItems",
					Lists.newArrayList("better_diving:fins, false, false, true, false, false, false, 0.1667", "better_diving:ultra_glide_fins, false, false, true, false, false, false, 0.3333", "better_diving:reinforced_fins, false, false, true, false, false, false, 0.1667"), o -> true);

			this.powerCell = new EnergyStorageItem(builder, "powerCell", 2_000_000, 5000, 500, 2_000_000);

			this.divingGear = new ArmorValues(builder, "divingGear", 60, 10, new Integer[] { 1, 3, 5, 1 }, 0.0D, 0.0D);
			this.improvedDivingGear = new ArmorValues(builder, "improvedDivingGear", 60, 10, new Integer[] { 1, 3, 5, 1 }, 0.0D, 0.0D);
			this.reinforcedDivingGear = new ArmorValues(builder, "reinforcedDivingGear", 80, 10, new Integer[] { 2, 5, 7, 2 }, 0.5D, 0.0D);

			this.mining = new Mining(builder);
			this.movement = new Movement(builder);
			this.ores = new Ores(builder);
			this.oxygen = new Oxygen(builder);
			this.seamoth = new Seamoth(builder);
			this.underwaterVisuals = new UnderwaterVisuals(builder);
		}

		public static class Mining {

			public final ForgeConfigSpec.DoubleValue breakSpeedAquaAffinity;
			public final ForgeConfigSpec.DoubleValue breakSpeedBase;

			public Mining(ForgeConfigSpec.Builder builder) {
				builder.comment("").push("mining");

				this.breakSpeedAquaAffinity = builder.comment("Break speed modifier underwater with the Aqua Affinity enchantment. (Vanilla = 5.0)").defineInRange("breakSpeedAquaAffinity", 1.25D, 0.0D, 10.0D);
				this.breakSpeedBase = builder.comment("Base break speed in water. (Vanilla = 0.2)").defineInRange("breakSpeedBase", 1.0D, 0.0D, 10.0D);

				builder.pop();
			}

		}

		public static class Movement {

			public final ForgeConfigSpec.DoubleValue baseSwimSpeed;

			public final ForgeConfigSpec.DoubleValue depthStriderAmount;
			public final ForgeConfigSpec.IntValue depthStriderOperation;

			public final ForgeConfigSpec.DoubleValue divingAmount;
			public final ForgeConfigSpec.IntValue divingOperation;

			public final ForgeConfigSpec.DoubleValue dolphinsGraceAmount;
			public final ForgeConfigSpec.IntValue dolphinsGraceOperation;

			public final ForgeConfigSpec.BooleanValue hungerModifier;
			public final ForgeConfigSpec.DoubleValue hungerThreshold;
			public final ForgeConfigSpec.DoubleValue hungerAmount;
			public final ForgeConfigSpec.IntValue hungerOperation;

			public final ForgeConfigSpec.BooleanValue mainhandModifier;
			public final ForgeConfigSpec.DoubleValue mainhandAmount;
			public final ForgeConfigSpec.IntValue mainhandOperation;

			public final ForgeConfigSpec.BooleanValue offhandModifier;
			public final ForgeConfigSpec.DoubleValue offhandAmount;
			public final ForgeConfigSpec.IntValue offhandOperation;

			public final ForgeConfigSpec.BooleanValue overwaterModifier;
			public final ForgeConfigSpec.DoubleValue overwaterAmount;
			public final ForgeConfigSpec.IntValue overwaterOperation;

            public final ForgeConfigSpec.BooleanValue weakerSneakDescending;

			public Movement(ForgeConfigSpec.Builder builder) {
				builder.comment("").push("movement");

				this.baseSwimSpeed = builder.comment("Base swim speed. (Vanilla = 0.02)").defineInRange("baseSwimSpeed", 0.04D, 0.0D, 1.0D);

				this.depthStriderAmount = builder.comment("Bonus swim speed with Depth Strider enchantment.").defineInRange("depthStriderAmount", 0.1D, -1.0D, 1.0D);
				this.depthStriderOperation = builder.comment("0 = add, 1 = multiply_base, 2 = mulitply (For more info check the minecraft wiki: https://minecraft.fandom.com/wiki/Attribute#Operations)").defineInRange("depthStriderOperation", 1, 0, 2);

				this.divingAmount = builder.comment("Bonus swim speed when diving. (Diving means being underwater and pressing sprint + forward)").defineInRange("divingAmount", 0.2D, -1.0D, 1.0D);
				this.divingOperation = builder.comment("0 = add, 1 = multiply_base, 2 = mulitply (For more info check the minecraft wiki: https://minecraft.fandom.com/wiki/Attribute#Operations)").defineInRange("divingOperation", 1, 0, 2);

				this.dolphinsGraceAmount = builder.comment("Bonus swim speed with Dolphin's Grace effect.").defineInRange("dolphinsGraceAmount", 1.0D, -1.0D, 1.0D);
				this.dolphinsGraceOperation = builder.comment("0 = add, 1 = multiply_base, 2 = mulitply (For more info check the minecraft wiki: https://minecraft.fandom.com/wiki/Attribute#Operations)").defineInRange("dolphinsGraceOperation", 1, 0, 2);

				this.hungerModifier = builder.comment("Enable/Disable swim speed penalty when having low hunger.").define("hungerModifier", true);
				this.hungerThreshold = builder.comment("Hunger threshold at which the swim speed will start to decrease. (Example with hungerThreshold = 0.2 hungerAmount = -0.5: swimSpeedPenalty at 0.1 hunger = -0.0,  swimSpeedPenalty at 0.1 hunger = -0.25,  swimSpeedPenalty at 0.0 hunger = -0.5)").defineInRange("hungerThreshold", 0.2D, 0.0D, 1.0D);
				this.hungerAmount = builder.comment("").defineInRange("hungerAmount", -0.5D, -1.0D, 1.0D);
				this.hungerOperation = builder.comment("0 = add, 1 = multiply_base, 2 = mulitply (For more info check the minecraft wiki: https://minecraft.fandom.com/wiki/Attribute#Operations)").defineInRange("hungerOperation", 2, 0, 2);

				this.mainhandModifier = builder.comment("Enable/Disable swim speed penalty when holding an item in the mainhand.").define("mainhandModifier", true);
				this.mainhandAmount = builder.comment("").defineInRange("mainhandAmount", -0.05D, -1.0D, 1.0D);
				this.mainhandOperation = builder.comment("0 = add, 1 = multiply_base, 2 = mulitply (For more info check the minecraft wiki: https://minecraft.fandom.com/wiki/Attribute#Operations)").defineInRange("mainhandOperation", 2, 0, 2);

				this.offhandModifier = builder.comment("Enable/Disable swim speed penalty when holding an item in the offhand.").define("offhandModifier", true);
				this.offhandAmount = builder.comment("").defineInRange("offhandAmount", -0.05D, -1.0D, 1.0D);
				this.offhandOperation = builder.comment("0 = add, 1 = multiply_base, 2 = mulitply (For more info check the minecraft wiki: https://minecraft.fandom.com/wiki/Attribute#Operations)").defineInRange("offhandOperation", 2, 0, 2);

				this.overwaterModifier = builder.comment("Enable/Disable swim speed bonus when swimming at the water surface.").define("overwaterModifier", true);
				this.overwaterAmount = builder.comment("").defineInRange("overwaterAmount", 0.2D, -1.0D, 1.0D);
				this.overwaterOperation = builder.comment("0 = add, 1 = multiply_base, 2 = mulitply (For more info check the minecraft wiki: https://minecraft.fandom.com/wiki/Attribute#Operations)").defineInRange("overwaterOperation", 1, 0, 2);

				this.weakerSneakDescending = builder.comment("When enabled sneaking in water only let's you descend slowly. The idea is that when you are in water you can still use items that require you to sneak without descending too far. Note that you can always use the descend key (default C) to descend while in water.").define("weakerSneakDescending", false);

				builder.pop();
			}

		}

		public static class Ores {

			public final OreConfig limestone;
			public final OreConfig sandstone;
			public final OreConfig shale;

			public Ores(ForgeConfigSpec.Builder builder) {
				builder.comment("").push("ores");

				this.limestone = new OreConfig(builder, "limestone", true, 1, 0, 2, 0, 64);
				this.sandstone = new OreConfig(builder, "sandstone", true, 1, 0, 1, 0, 40);
				this.shale = new OreConfig(builder, "shale", true, 1, 3, 8, 0, 64);
	
				builder.pop();
			}

			public static class OreConfig {

				public final ForgeConfigSpec.BooleanValue enabled;
				public final ForgeConfigSpec.IntValue chance;
				public final ForgeConfigSpec.IntValue minAmount;
				public final ForgeConfigSpec.IntValue maxAmount;
				public final ForgeConfigSpec.IntValue minHeight;
				public final ForgeConfigSpec.IntValue maxHeight;

				public OreConfig(ForgeConfigSpec.Builder builder, String name, boolean enabled, int chance, int minAmount, int maxAmount, int minHeight, int maxHeight) {
					builder.comment("").push(name);

					this.enabled = builder.comment("").define("enabled", enabled);
					this.chance = builder.comment("").defineInRange("chance", chance, 0, 1024);
					this.minAmount = builder.comment("").defineInRange("minAmount", minAmount, 0, 1024);
					this.maxAmount = builder.comment("").defineInRange("maxAmount", maxAmount, 0, 1024);
					this.minHeight = builder.comment("").defineInRange("minHeight", minHeight, -1024, 1024);
					this.maxHeight = builder.comment("").defineInRange("maxHeight", maxHeight, -1024, 1024);

					builder.pop();
				}
				
			}

		}

		public static class Oxygen {

			public final ForgeConfigSpec.IntValue oxygenBaseDivingDepth;
			public final ForgeConfigSpec.IntValue oxygenCapacity;
			public final ForgeConfigSpec.IntValue oxygenCapacityRespiration;
			public final ForgeConfigSpec.BooleanValue oxygenEfficiency;
			public final ForgeConfigSpec.IntValue oxygenEfficiencyRate;
			public final ForgeConfigSpec.IntValue oxygenRefillRate;

			public Oxygen(ForgeConfigSpec.Builder builder) {
				builder.comment("").push("oxygen");

				this.oxygenBaseDivingDepth = builder.comment("Base diving depth. (Check 'oxygenEfficiencyRate' for more information)").defineInRange("oxygenBaseDivingDepth", 20, 0, 1024);
				this.oxygenCapacity = builder.comment("Base oxygen capacity in ticks. (20 ticks = 1 second)").defineInRange("oxygenCapacity", 900, 0, 1_000_000);
				this.oxygenCapacityRespiration = builder.comment("").defineInRange("oxygenCapacityRespiration", 300, 0, 1_000_000);
				this.oxygenEfficiency = builder.comment("Enable/Disable decreased oxygen efficiency while diving deeper.").define("oxygenEfficiency", true);
				this.oxygenEfficiencyRate = builder.comment("Every x blocks below the max diving depth (defined by 'oxygenBaseDivingDepth' and equipment) consumes one unit of oxygen more per second.").defineInRange("oxygenEfficiencyRate", 4, 1, 1024);
				this.oxygenRefillRate = builder.comment("").defineInRange("oxygenRefillRate", 25, 1, 100);

				builder.pop();
			}

		}

		public static class Seamoth {

			public final ForgeConfigSpec.DoubleValue seamothSpeed;
			public final ForgeConfigSpec.IntValue seamothEnergyUsage;

			public Seamoth(ForgeConfigSpec.Builder builder) {
				builder.comment("").push("seamoth");

				this.seamothSpeed = builder.comment("Speed of the seamoth. (blocks per second = x * 400)").defineInRange("seamothSpeed", 0.0275D, 0.0D, 1.0D);
				this.seamothEnergyUsage = builder.comment("Energy usage of the seamoth per tick.").defineInRange("seamothEnergyUsage", 100, 0, 1_000_000);

				builder.pop();
			}

		}

		public static class EnergyStorageItem {

			public final ForgeConfigSpec.IntValue capacity;
			public final ForgeConfigSpec.IntValue maxReceive;
			public final ForgeConfigSpec.IntValue maxExtract;
			public final ForgeConfigSpec.IntValue energy;

			public EnergyStorageItem(ForgeConfigSpec.Builder builder, String name, int capacity, int maxReceive, int maxExtract, int energy) {
				builder.comment("").push(name);

				this.capacity = builder.comment("").defineInRange("capacity", capacity, 0, Integer.MAX_VALUE);
				this.maxReceive = builder.comment("").defineInRange("maxReceive", maxReceive, 0, Integer.MAX_VALUE);
				this.maxExtract = builder.comment("").defineInRange("maxExtract", maxExtract, 0, Integer.MAX_VALUE);
				this.energy = builder.comment("").defineInRange("energy", energy, 0, Integer.MAX_VALUE);

				builder.pop();
			}

		}

		public static class ArmorValues {

			public final ForgeConfigSpec.IntValue durability;
			public final ForgeConfigSpec.IntValue enchantability;
			public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> protection;
			public final ForgeConfigSpec.DoubleValue toughness;
			public final ForgeConfigSpec.DoubleValue knockbackResistance;

			public ArmorValues(ForgeConfigSpec.Builder builder, String name, int durability, int enchantability, Integer[] protection, double toughness, double knockbackResistance) {
				builder.comment("").push(name);

				this.durability = builder.comment("").defineInRange("durability", durability, 0, 1_000_000);
				this.enchantability = builder.comment("").defineInRange("enchantability", enchantability, 0, 1000);
				this.protection = builder.comment("").defineList("protection", Arrays.asList(protection), o -> true);
				this.toughness = builder.comment("").defineInRange("toughness", toughness, 0.0D, 1000.0D);
				this.knockbackResistance = builder.comment("").defineInRange("knockbackResistance", knockbackResistance, -1.0D, 1.0D);

				builder.pop();
			}

		}

		public static class UnderwaterVisuals {

			public final ForgeConfigSpec.DoubleValue brightnessDay;
			public final ForgeConfigSpec.DoubleValue brightnessNight;
			public final ForgeConfigSpec.DoubleValue fogDensityDay;
			public final ForgeConfigSpec.DoubleValue fogDensityNight;
			public final ForgeConfigSpec.DoubleValue fogBrightnessDay;
			public final ForgeConfigSpec.DoubleValue fogBrightnessNight;

			public UnderwaterVisuals(ForgeConfigSpec.Builder builder) {
				builder.comment("").push("underwater_visuals");

				this.brightnessDay = builder.comment("(Vanilla = 0.0)").defineInRange("brightnessDay", 0.3D, 0.0D, 1.0D);
				this.brightnessNight = builder.comment("(Vanilla = 0.0)").defineInRange("brightnessNight", 0.1D, 0.0D, 1.0D);
				this.fogBrightnessDay = builder.comment("(Vanilla = 1.0)").defineInRange("fogBrightnessDay", 1.0D, 0.0D, 1.0D);
				this.fogBrightnessNight = builder.comment("(Vanilla = 1.0)").defineInRange("fogBrightnessNight", 0.05D, 0.0D, 1.0D);
				this.fogDensityDay = builder.comment("(Vanilla = 0.02)").defineInRange("fogDensityDay", 0.015D, 0.0D, 1.0D);
				this.fogDensityNight = builder.comment("(Vanilla = 0.02)").defineInRange("fogDensityNight", 0.025D, 0.0D, 1.0D);
				
				builder.pop();
			}

		}

	}

	public static class ClientConfig {

		public final ForgeConfigSpec.IntValue oxygenGuiAnchor;
		public final ForgeConfigSpec.BooleanValue oxygenGuiEnabled;
		public final ForgeConfigSpec.IntValue oxygenGuiOffsetX;
		public final ForgeConfigSpec.IntValue oxygenGuiOffsetY;
		public final ForgeConfigSpec.BooleanValue oxygenGuiRenderAlways;
		public final ForgeConfigSpec.BooleanValue oxygenGuiRenderNotFull;
		public final ForgeConfigSpec.BooleanValue oxygenGuiRenderUnderwater;

		public final ForgeConfigSpec.IntValue seamothGuiAnchor;
		public final ForgeConfigSpec.BooleanValue seamothGuiEnabled;
		public final ForgeConfigSpec.IntValue seamothGuiOffsetX;
		public final ForgeConfigSpec.IntValue seamothGuiOffsetY;

		public final ForgeConfigSpec.BooleanValue skipSkyRendering;

		public ClientConfig(ForgeConfigSpec.Builder builder) {
			this.oxygenGuiAnchor = builder.comment("0: top-left, 1: top-middle, 2: top-right, 3: bottom-right, 4: bottom-middle, 5: bottom-left").defineInRange("oxygenGuiAnchor", 4, 0, 5);
			this.oxygenGuiEnabled = builder.comment("").define("oxygenGuiEnabled", true);
			this.oxygenGuiOffsetX = builder.comment("").defineInRange("oxygenGuiOffsetX", 62, -1000, 1000);
			this.oxygenGuiOffsetY = builder.comment("").defineInRange("oxygenGuiOffsetY", -32, -1000, 1000);
			this.oxygenGuiRenderAlways = builder.comment("If enabled the oxygen gui will always be rendered.").define("oxygenGuiRenderAlways", false);
			this.oxygenGuiRenderNotFull = builder.comment("If enabled the oxygen gui will be rendered when current oxygen is less than the oxygen capacity.").define("oxygenGuiRenderNotFull", true);
			this.oxygenGuiRenderUnderwater = builder.comment("If enabled the oxygen gui will be rendered when underwater.").define("oxygenGuiRenderUnderwater", true);

			this.seamothGuiAnchor = builder.comment("0: top-left, 1: top-middle, 2: top-right, 3: bottom-right, 4: bottom-middle, 5: bottom-left").defineInRange("seamothGuiAnchor", 3, 0, 5);
			this.seamothGuiEnabled = builder.comment("").define("seamothGuiEnabled", true);
			this.seamothGuiOffsetX = builder.comment("").defineInRange("seamothGuiOffsetX", -4, -1000, 1000);
			this.seamothGuiOffsetY = builder.comment("").defineInRange("seamothGuiOffsetY", -8, -1000, 1000);

			this.skipSkyRendering = builder.comment("").define("skipSkyRendering", true);
		}

	}

}
