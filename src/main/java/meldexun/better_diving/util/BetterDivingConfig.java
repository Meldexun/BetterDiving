package meldexun.better_diving.util;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.network.packet.CPacketUpdateConfigHelper;
import meldexun.better_diving.util.config.DivingGearConfig;
import meldexun.better_diving.util.config.EntityConfig;
import meldexun.better_diving.util.config.GeneratorConfig;
import meldexun.better_diving.util.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = BetterDiving.MOD_ID)
public class BetterDivingConfig {

	@Config.LangKey("config.category_general")
	@Config.Comment("(server only)")
	public static final General GENERAL = new General();

	public static class General {
		@Config.Comment("Enable/Disable changes to item entity sinking in water.")
		public boolean itemEntityMovement = true;
		@Config.Comment("Factor in percent how fast item entities sink in water.")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double itemEntityMovementFactor = 0.125D;
		@Config.Comment("Enable/Disable packets to sync oxygen. Should prevent desync of client and server. But increases network traffic and comes with a latency. Not recommended unless you have experienced desync.")
		public boolean oxygenSyncPackets = false;
		@Config.Comment("Enable/Disable packets to sync seamoth energy. Should prevent desync of client and server. But increases network traffic and comes with a latency. Not recommended unless you have experienced desync.")
		public boolean seamothEnergySyncPackets = false;
		@Config.Comment("When 'divingMovement' module is enabled the movemet will be altered to be like in MC 1.13.")
		public boolean vanillaDivingMovement = false;
		@Config.Comment("Lower swim speed limit in percent.")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double swimSpeedLimitLower = 0.33333333D;
		@Config.Comment("Upper swim speed limit in percent.")
		@Config.RangeDouble(min = 1.0D, max = 2.0D)
		public double swimSpeedLimitUpper = 1.33333333D;
	}

	@Config.LangKey("config.category_modules")
	@Config.Comment("")
	public static final Modules MODULES = new Modules();

	public static class Modules {
		@Config.Comment("(server only)")
		public boolean blockBreaking = true;
		@Config.Comment("(server only)")
		public boolean divingMovement = true;
		@Config.Comment("(server only)")
		public boolean entitySpawning = true;
		@Config.Comment("(server only)")
		public boolean oreGeneration = true;
		@Config.Comment("(server only)")
		public boolean oxygenHandling = true;
		@Config.Comment("(server only)")
		public boolean plantGeneration = true;
		@Config.Comment("(client only)")
		public boolean visionUnderWater = true;
	}

	@Config.LangKey("config.category_ores")
	@Config.Comment("(server only)")
	@Config.RequiresMcRestart
	public static final Ores ORES = new Ores();

	public static class Ores {
		@Config.LangKey("config.limestone")
		@Config.Comment("")
		public GeneratorConfig limestone = new GeneratorConfig(true, 2, 1, 5, 60, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.sandstone")
		@Config.Comment("")
		public GeneratorConfig sandstone = new GeneratorConfig(true, 4, 1, 5, 40, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.sand_layer")
		@Config.Comment("")
		public SandLayer sandLayer = new SandLayer();

		public class SandLayer {
			@Config.Comment("Enable/Disable sand layer generation.")
			public boolean shouldGenerate = true;
			@Config.Comment("")
			@Config.RangeInt(min = 0, max = 256)
			public int minHeight = 44;
			@Config.Comment("")
			@Config.RangeInt(min = 0, max = 256)
			public int maxHeight = 60;
			@Config.Comment("Biomes in which this plant/ore should generate. For Mod-Biomes: modid:biome_name. Definitely supported mods: BiomesOPlenty.")
			public String[] biomes = new String[] { "ocean", "deep_ocean" };
			@Config.Comment("Dimensions in which this plant/ore should generate.")
			public int[] dimensions = new int[] { 0 };
		}
	}

	@Config.LangKey("config.category_plants")
	@Config.Comment("(server only)")
	@Config.RequiresMcRestart
	public static final Plants PLANTS = new Plants();

	public static class Plants {
		@Config.LangKey("config.acid_mushroom")
		@Config.Comment("")
		public GeneratorConfig acidMushroom = new GeneratorConfig(true, 14, 8, 5, 50, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.creepvine")
		@Config.Comment("")
		public GeneratorConfig creepvine = new GeneratorConfig(true, 14, 16, 5, 40, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.seagrass")
		@Config.Comment("")
		public GeneratorConfig seagrass = new GeneratorConfig(true, 1, 24, 5, 60, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.seagrass_tall")
		@Config.Comment("")
		public GeneratorConfig seagrassTall = new GeneratorConfig(true, 4, 6, 5, 60, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });

		@Config.Comment("")
		public boolean shouldGenerateCreepvineSeedCluster = true;
	}

	@Config.LangKey("config.category_diving_values")
	@Config.Comment("(server only)")
	@Config.RequiresMcRestart
	public static final DivingValues DIVING_VALUES = new DivingValues();

	public static class DivingValues {
		@Config.Comment("Oxygen capacity in ticks (20 ticks = 1 second)")
		@Config.RangeInt(min = 0, max = 10000)
		public int airBase = 900;
		@Config.Comment("Enable/Disable decreased oxygen efficiency while diving deeper")
		public boolean airEfficiency = true;
		@Config.Comment("When 'airEfficiency' is true: diving every 'this value' blocks deep consumes 100% more air")
		@Config.RangeInt(min = 1, max = 256)
		public int airEfficiencyLimit = 30;
		@Config.Comment("Oxygen capacity in ticks (20 ticks = 1 second)")
		@Config.RangeInt(min = 0, max = 10000)
		public int airPerRespirationLevel = 300;

		@Config.Comment("Break speed underwater based on normal break speed")
		@Config.RangeDouble(min = 0.0D, max = 2.0D)
		public double breakSpeed = 1.0D;
		@Config.Comment("Break speed bonus underwater with the Aqua Affinity Enchantment")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double breakSpeedAquaAffinity = 0.1D;

		@Config.Comment("Speed of the seamoth. (blocks per second = 'this value' / 7.5 * 100 * 20)")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double seamothSpeed = 0.052D;
		@Config.Comment("Amount of energy the seamoth uses per second")
		@Config.RangeInt(min = 0, max = 100)
		public int seamothEnergyUsage = 25;

		@Config.Comment("Basic swim speed. (min = 'this value' / 3; max = 'this value' * 4 / 3; vanilla ~ 0.0196D) (blocks per second = 'this value' / 20 * 100 * 20)")
		@Config.RangeDouble(min = 0.0D, max = 0.1D)
		public double swimSpeed = 0.0392D;
		@Config.Comment("Depth strider bonus swim speed in percent")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double swimSpeedDepthStrider = 0.1D;
	}

	@Config.LangKey("config.category_diving_gear")
	@Config.Comment("(server only)")
	@Config.RequiresMcRestart
	public static final DivingGear DIVING_GEAR = new DivingGear();

	public static class DivingGear {
		@Config.LangKey("config.diving_gear")
		@Config.Comment("")
		public DivingGearConfig basicDivingGear = new DivingGearConfig(0.16666667D, false, false, 0.1D, 600, -0.07105263D, 50, 10, new int[] { 1, 3, 5, 1 }, 0.0F);
		@Config.LangKey("config.improved_diving_gear")
		@Config.Comment("")
		public DivingGearConfig improvedDivingGear = new DivingGearConfig(0.33333333D, true, false, 0.2D, 1800, -0.10614035D, 50, 10, new int[] { 1, 3, 5, 1 }, 0.0F);
		@Config.LangKey("config.reinforced_diving_gear")
		@Config.Comment("")
		public DivingGearConfig reinforcedDivingGear = new DivingGearConfig(0.16666667D, false, true, 0.1D, 600, -0.07105263D, 60, 10, new int[] { 2, 5, 7, 2 }, 0.0F);
	}

	@Config.LangKey("config.category_client_settings")
	@Config.Comment("(client only)")
	public static final ClientSettings CLIENT_SETTINGS = new ClientSettings();

	public static class ClientSettings {
		@Config.LangKey("config.gui_oxygen")
		@Config.Comment("")
		public GuiConfig guiOxygenConfig = new GuiConfig(true, 4, -116, 0);
		@Config.LangKey("config.gui_seamoth")
		@Config.Comment("")
		public GuiConfig guiSeamothConfig = new GuiConfig(true, 3, -1, -1);
		@Config.LangKey("config.fog_settings")
		@Config.Comment("")
		public Fog fogSettings = new Fog();

		@Config.Comment("0: auto, 1: 1080p, 2: 1440p, 3: 2160p")
		@Config.RangeInt(min = 0, max = 3)
		public int autoResolution = 0;
		@Config.Comment("0: oxygen gui always enabled, 1: oxygen gui enabled while in water, 2: oxygen gui enabled while under water")
		@Config.RangeInt(min = 0, max = 2)
		public int guiOxygen = 2;
		@Config.Comment("Hides the water overlay when wearing a diving gear helmet or when inside a vehicle.")
		public boolean hideWaterOverlay = true;
		@Config.Comment("Enable/Disable underwater Ambience.")
		public boolean underWaterAmbience = true;

		public class Fog {
			@Config.Comment("Enable/Disable fog color changes.")
			public boolean fogColorEnabled = false;
			@Config.Comment("Enable/Disable fog density changes.")
			public boolean fogDensityEnabled = true;

			@Config.Comment("")
			@Config.RangeDouble(min = 0.0001D, max = 0.1D)
			public double fogDensity = 0.016D;
			@Config.Comment("Fog density bonus in percent per block under water.")
			@Config.RangeDouble(min = -1.0D, max = 1.0D)
			public double fogDensityBlocksUnderWater = 0.005D;
			@Config.Comment("Fog density bonus in percent during nighttime.")
			@Config.RangeDouble(min = -1.0D, max = 1.0D)
			public double fogDensityNight = 1.0D;
			@Config.Comment("")
			@Config.RangeDouble(min = 0.0D, max = 1.0D)
			public double[] fogColor = new double[] { 0.1D, 0.4D, 0.9D };
			@Config.Comment("Fog color bonus in percent per block under water.")
			@Config.RangeDouble(min = -1.0D, max = 1.0D)
			public double[] fogColorBlocksUnderWater = new double[] { -0.01D, -0.01D, -0.01D };
			@Config.Comment("Fog color bonus in percent during nighttime.")
			@Config.RangeDouble(min = -1.0D, max = 1.0D)
			public double[] fogColorNight = new double[] { -0.9D, -0.9D, -0.9D };
		}
	}

	@Config.LangKey("config.category_entity_settings")
	@Config.Comment("(server only)")
	@Config.RequiresMcRestart
	public static final EntitySettings ENTITY_SETTINGS = new EntitySettings();

	public static class EntitySettings {
		@Config.LangKey("config.peeper")
		public EntityConfig peeper = new EntityConfig(true, 2, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.bladderfish")
		public EntityConfig bladderfish = new EntityConfig(true, 2, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.garryfish")
		public EntityConfig garryfish = new EntityConfig(true, 2, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.holefish")
		public EntityConfig holefish = new EntityConfig(true, 2, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.boomerang")
		public EntityConfig boomerang = new EntityConfig(true, 2, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.RangeInt(min = 1, max = 1000000)
		@Config.Comment("For every chunk it chooses a random position every x ticks and then tries to spawn a random entity.")
		public int chance = 1200;
		@Config.Comment("Used to check how many fish are in range to determine whether to spawn more fish.")
		public int range = 16;
		@Config.Comment("Only spawns more fish when there are less than x fish in range.")
		public int limit = 1;
	}

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(BetterDiving.MOD_ID)) {
				ConfigManager.sync(BetterDiving.MOD_ID, Config.Type.INSTANCE);
				if (Minecraft.getMinecraft().isSingleplayer()) {
					BetterDivingConfigClient.update();
					BetterDiving.CONNECTION.sendToServer(new CPacketUpdateConfigHelper());
				}
			}
		}

	}

}
