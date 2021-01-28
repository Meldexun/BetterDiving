package meldexun.better_diving.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

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

	public static class ServerConfig {

		public final ForgeConfigSpec.DoubleValue breakSpeedAquaAffinity;
		public final ForgeConfigSpec.BooleanValue breakSpeedChanges;
		public final ForgeConfigSpec.BooleanValue movementChanges;
		public final ForgeConfigSpec.BooleanValue oxygenChanges;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> oxygenProviderEntities;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> oxygenProviderItems;

		public final Movement movement;
		public final Oxygen oxygen;

		public ServerConfig(ForgeConfigSpec.Builder builder) {
			this.breakSpeedAquaAffinity = builder.comment("").defineInRange("breakSpeedAquaAffinity", 0.25D, 0.0D, 10.0D);
			this.breakSpeedChanges = builder.comment("").define("breakSpeedChanges", true);
			this.movementChanges = builder.comment("").define("movementChanges", true);
			this.oxygenChanges = builder.comment("").define("oxygenChanges", true);
			this.oxygenProviderEntities = builder.comment("modid:entity, oxygenCapacity").defineList("oxygenProviderEntities", new ArrayList<>(), o -> true);
			this.oxygenProviderItems = builder.comment("modid:item, oxygenCapacity, equipmentSlots, maxDivingDepth").defineList("oxygenProviderItems", new ArrayList<>(), o -> true);

			this.movement = new Movement(builder);
			this.oxygen = new Oxygen(builder);
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

			public Movement(ForgeConfigSpec.Builder builder) {
				builder.push("movement");

				this.baseSwimSpeed = builder.comment("").defineInRange("baseSwimSpeed", 0.04D, 0.0D, 1.0D);

				this.depthStriderAmount = builder.comment("").defineInRange("depthStriderAmount", 0.1D, -1.0D, 1.0D);
				this.depthStriderOperation = builder.comment("").defineInRange("depthStriderOperation", 1, 0, 2);

				this.divingAmount = builder.comment("").defineInRange("divingAmount", 0.5D, -1.0D, 1.0D);
				this.divingOperation = builder.comment("").defineInRange("divingOperation", 1, 0, 2);

				this.dolphinsGraceAmount = builder.comment("").defineInRange("dolphinsGraceAmount", 1.0D, -1.0D, 1.0D);
				this.dolphinsGraceOperation = builder.comment("").defineInRange("dolphinsGraceOperation", 1, 0, 2);

				this.hungerModifier = builder.comment("").define("hungerModifier", true);
				this.hungerThreshold = builder.comment("").defineInRange("hungerThreshold", 0.2D, 0.0D, 1.0D);
				this.hungerAmount = builder.comment("").defineInRange("hungerAmount", -0.5D, -1.0D, 1.0D);
				this.hungerOperation = builder.comment("").defineInRange("hungerOperation", 2, 0, 2);

				this.mainhandModifier = builder.comment("").define("mainhandModifier", true);
				this.mainhandAmount = builder.comment("").defineInRange("mainhandAmount", -0.05D, -1.0D, 1.0D);
				this.mainhandOperation = builder.comment("").defineInRange("mainhandOperation", 2, 0, 2);

				this.offhandModifier = builder.comment("").define("offhandModifier", true);
				this.offhandAmount = builder.comment("").defineInRange("offhandAmount", -0.05D, -1.0D, 1.0D);
				this.offhandOperation = builder.comment("").defineInRange("offhandOperation", 2, 0, 2);

				this.overwaterModifier = builder.comment("").define("overwaterModifier", true);
				this.overwaterAmount = builder.comment("").defineInRange("overwaterAmount", 0.25D, -1.0D, 1.0D);
				this.overwaterOperation = builder.comment("").defineInRange("overwaterOperation", 1, 0, 2);

				builder.pop();
			}

		}

		public static class Oxygen {

			public final ForgeConfigSpec.IntValue oxygenCapacity;
			public final ForgeConfigSpec.IntValue oxygenCapacityRespiration;
			public final ForgeConfigSpec.BooleanValue oxygenEfficiency;
			public final ForgeConfigSpec.IntValue oxygenEfficiencyRate;

			public Oxygen(ForgeConfigSpec.Builder builder) {
				builder.push("oxygen");

				this.oxygenCapacity = builder.comment("").defineInRange("oxygenCapacity", 900, 0, 1000000);
				this.oxygenCapacityRespiration = builder.comment("").defineInRange("oxygenCapacityRespiration", 300, 0, 1000000);
				this.oxygenEfficiency = builder.comment("").define("oxygenEfficiency", true);
				this.oxygenEfficiencyRate = builder.comment("").defineInRange("oxygenEfficiencyRate", 32, 0, 1000);

				builder.pop();
			}

		}

	}

	public static class ClientConfig {

		public final ForgeConfigSpec.IntValue oxygenGuiAnchor;
		public final ForgeConfigSpec.BooleanValue oxygenGuiEnabled;
		public final ForgeConfigSpec.IntValue oxygenGuiOffsetX;
		public final ForgeConfigSpec.IntValue oxygenGuiOffsetY;

		public ClientConfig(ForgeConfigSpec.Builder builder) {
			this.oxygenGuiAnchor = builder.comment("0: top-left, 1: top-middle, 2: top-right, 3: bottom-right, 4: bottom-middle, 5: bottom-left").defineInRange("oxygenGuiAnchor", 4, 0, 5);
			this.oxygenGuiEnabled = builder.comment("").define("oxygenGuiEnabled", true);
			this.oxygenGuiOffsetX = builder.comment("").defineInRange("oxygenGuiOffsetX", 61, -1000, 1000);
			this.oxygenGuiOffsetY = builder.comment("").defineInRange("oxygenGuiOffsetY", -36, -1000, 1000);
		}

	}

}
