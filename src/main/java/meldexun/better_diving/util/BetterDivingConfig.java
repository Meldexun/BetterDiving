package meldexun.better_diving.util;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.network.packet.SPacketSyncConfig;
import meldexun.better_diving.util.config.DivingGearConfig;
import meldexun.better_diving.util.config.EntityConfig;
import meldexun.better_diving.util.config.GeneratorConfig;
import meldexun.better_diving.util.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Config(modid = BetterDiving.MOD_ID)
public class BetterDivingConfig {

	/** Use {@link #getInstance()} instead. */
	@Config.LangKey("config.better_diving")
	@Config.Comment("")
	public static final BetterDivingConfig MASTER_CONFIG = new BetterDivingConfig();

	/** Use {@link #getInstance()} instead. */
	@Config.Ignore
	public static final BetterDivingConfig SLAVE_CONFIG = new BetterDivingConfig();

	@ByteBufHelper.Sync
	@Config.LangKey("config.category_general")
	@Config.Comment("")
	public final General general = new General();

	@ByteBufHelper.Sync
	@Config.LangKey("config.category_modules")
	@Config.Comment("")
	public final Modules modules = new Modules();

	@Config.LangKey("config.category_ores")
	@Config.Comment("")
	public final Ores ores = new Ores();

	@Config.LangKey("config.category_plants")
	@Config.Comment("")
	public final Plants plants = new Plants();

	@ByteBufHelper.Sync
	@Config.LangKey("config.category_diving_gear")
	@Config.Comment("")
	public final DivingGear divingGear = new DivingGear();

	@ByteBufHelper.Sync
	@Config.LangKey("config.category_diving_values")
	@Config.Comment("")
	public final DivingValues divingValues = new DivingValues();

	@Config.LangKey("config.category_client_settings")
	@Config.Comment("")
	public final Client client = new Client();

	@Config.LangKey("config.category_entity_settings")
	@Config.Comment("")
	public final Entities entities = new Entities();

	public static BetterDivingConfig getInstance() {
		return BetterDivingConfig.SLAVE_CONFIG;
	}

	public static class General {
		@ByteBufHelper.Sync
		@Config.Comment("Enable/Disable changes to item entity sinking in water.")
		public boolean itemEntityMovement = true;
		@ByteBufHelper.Sync
		@Config.Comment("Factor in percent how fast item entities sink in water.")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double itemEntityMovementFactor = 0.125D;

		@ByteBufHelper.Sync
		@Config.Comment("Enable/Disable packets to sync oxygen. Should prevent desync of client and server. But increases network traffic and comes with a latency. Not recommended unless you have experienced desync.")
		public boolean oxygenSyncPackets = false;
		@ByteBufHelper.Sync
		@Config.Comment("Enable/Disable packets to sync seaglide energy. Should prevent desync of client and server. But increases network traffic and comes with a latency. Not recommended unless you have experienced desync.")
		public boolean seaglideEnergySyncPackets = false;
		@ByteBufHelper.Sync
		@Config.Comment("Enable/Disable packets to sync seamoth energy. Should prevent desync of client and server. But increases network traffic and comes with a latency. Not recommended unless you have experienced desync.")
		public boolean seamothEnergySyncPackets = false;

		@ByteBufHelper.Sync
		@Config.Comment("When 'divingMovement' module is enabled the movemet will be altered to be similar to MC 1.13.")
		public boolean vanillaDivingMovement = false;

		@ByteBufHelper.Sync
		@Config.Comment("Enable/Disable resizing of the player when sprint diving or in a seamoth.")
		public boolean playerResizing = true;
		@ByteBufHelper.Sync
		@Config.Comment("When 'playerResizing' is enabled and ArtemisLib is loaded the player will be resized using ArtemisLib. Might cause visual and/or eye height glitches.")
		public boolean artemisLibCompatibility = true;
	}

	public static class Modules {
		@ByteBufHelper.Sync
		@Config.Comment("")
		public boolean blockBreaking = true;
		@ByteBufHelper.Sync
		@Config.Comment("")
		public boolean divingMovement = true;
		@Config.Comment("")
		public boolean entitySpawning = true;
		@Config.Comment("")
		public boolean oreGeneration = true;
		@ByteBufHelper.Sync
		@Config.Comment("")
		public boolean oxygenHandling = true;
		@Config.Comment("")
		public boolean plantGeneration = true;
		@Config.Comment("")
		public boolean visionUnderWater = true;
	}

	public static class Ores {
		@Config.LangKey("config.limestone")
		@Config.Comment("")
		public GeneratorConfig limestone = new GeneratorConfig(true, 2, 1, 4, 60, true, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.sandstone")
		@Config.Comment("")
		public GeneratorConfig sandstone = new GeneratorConfig(true, 4, 1, 24, 60, true, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.sand_layer")
		@Config.Comment("")
		public SandLayer sandLayer = new SandLayer();

		public class SandLayer {
			@Config.Comment("Enable/Disable sand layer generation.")
			public boolean shouldGenerate = true;
			@Config.Comment("Usage changes depending on seaLevelRelative.")
			@Config.RangeInt(min = 0, max = 256)
			public int minHeight = 4;
			@Config.Comment("Usage changes depending on seaLevelRelative.")
			@Config.RangeInt(min = 0, max = 256)
			public int maxHeight = 20;
			@Config.Comment("When enabled minHeight defines how many blocks below sea level you have to at least be to find this plant/ore and maxHeight defines how many blocks below sea level you have to at most be to find this plant/ore.")
			public boolean seaLevelRelative = true;
			@Config.Comment("Set this to true to generate this plant/ore in every biome.")
			@Config.Ignore
			public boolean biomeOverride = false;
			@Config.Comment("Biome types in which this plant/ore can generate.")
			public String[] biomeTypes = new String[] { "OCEAN" };
			@Config.Comment("Biomes in which this plant/ore should generate. For modded biomes: modid:biome_name.")
			public String[] biomes = new String[] { "ocean", "deep_ocean" };
			@Config.Comment("Dimensions in which this plant/ore should generate.")
			public int[] dimensions = new int[] { 0 };

			public boolean isEnabled() {
				if (this.dimensions.length == 0) {
					return false;
				}
				if (!this.biomeOverride && this.biomeTypes.length == 0 && this.biomes.length == 0) {
					return false;
				}
				return this.shouldGenerate;
			}

			public ResourceLocation[] getBiomes() {
				ResourceLocation[] biomeRegistryNames = new ResourceLocation[this.biomes.length];
				for (int i = 0; i < this.biomes.length; i++) {
					biomeRegistryNames[i] = new ResourceLocation(this.biomes[i]);
				}
				return biomeRegistryNames;
			}
		}
	}

	public static class Plants {
		@Config.LangKey("config.acid_mushroom")
		@Config.Comment("")
		public GeneratorConfig acidMushroom = new GeneratorConfig(true, 12, 8, 4, 60, true, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.creepvine")
		@Config.Comment("")
		public GeneratorConfig creepvine = new GeneratorConfig(true, 16, 16, 16, 60, true, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.seagrass")
		@Config.Comment("")
		public GeneratorConfig seagrass = new GeneratorConfig(true, 1, 24, 4, 60, true, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.seagrass_tall")
		@Config.Comment("")
		public GeneratorConfig seagrassTall = new GeneratorConfig(true, 4, 12, 4, 60, true, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });

		@Config.Comment("")
		public boolean shouldGenerateCreepvineSeedCluster = true;
	}

	public static class DivingGear {
		@ByteBufHelper.Sync
		@Config.LangKey("config.diving_gear")
		@Config.Comment("")
		public DivingGearConfig basicDivingGear = new DivingGearConfig(0.1667D, false, false, 0.15D, 600, -0.08D, 60, 10, new int[] { 1, 3, 5, 1 }, 0.0F);
		@ByteBufHelper.Sync
		@Config.LangKey("config.improved_diving_gear")
		@Config.Comment("")
		public DivingGearConfig improvedDivingGear = new DivingGearConfig(0.3333D, true, false, 0.3D, 1800, -0.08D, 60, 10, new int[] { 1, 3, 5, 1 }, 0.0F);
		@ByteBufHelper.Sync
		@Config.LangKey("config.reinforced_diving_gear")
		@Config.Comment("")
		public DivingGearConfig reinforcedDivingGear = new DivingGearConfig(0.1667D, false, true, 0.15D, 600, -0.08D, 80, 10, new int[] { 2, 5, 7, 2 }, 0.5F);
	}

	public static class DivingValues {
		@ByteBufHelper.Sync
		@Config.Comment("Oxygen capacity in ticks (20 ticks = 1 second)")
		@Config.RangeInt(min = 0, max = 10000)
		public int airBase = 900;
		@ByteBufHelper.Sync
		@Config.Comment("Enable/Disable decreased oxygen efficiency while diving deeper")
		public boolean airEfficiency = true;
		@ByteBufHelper.Sync
		@Config.Comment("When 'airEfficiency' is enabled, diving every x blocks deep consumes one unit of oxygen more per second.")
		@Config.RangeInt(min = 1, max = 256)
		public int airEfficiencyLimit = 32;
		@ByteBufHelper.Sync
		@Config.Comment("Oxygen capacity in ticks (20 ticks = 1 second)")
		@Config.RangeInt(min = 0, max = 10000)
		public int airPerRespirationLevel = 300;

		@ByteBufHelper.Sync
		@Config.Comment("Break speed underwater based on normal break speed")
		@Config.RangeDouble(min = 0.0D, max = 2.0D)
		public double breakSpeed = 1.0D;
		@ByteBufHelper.Sync
		@Config.Comment("Break speed bonus underwater with the Aqua Affinity Enchantment")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double breakSpeedAquaAffinity = 0.15D;

		@ByteBufHelper.Sync
		@Config.Comment("Amount of energy the seaglide uses per tick (20 ticks = 1 second)")
		@Config.RangeInt(min = 0, max = 1000)
		public int seaglideEnergyUsage = 65;
		@ByteBufHelper.Sync
		@Config.Comment("Seaglide swim speed. (blocks per second = x * 100)")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double seaglideSpeed = 0.11D;

		@ByteBufHelper.Sync
		@Config.Comment("Amount of energy the seamoth uses per tick (20 ticks = 1 second)")
		@Config.RangeInt(min = 0, max = 1000)
		public int seamothEnergyUsage = 130;
		@ByteBufHelper.Sync
		@Config.Comment("Speed of the seamoth. (blocks per second = x * 100 * 20 / 5)")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double seamothSpeed = 0.0275D;

		@ByteBufHelper.Sync
		@Config.Comment("Basic swim speed. (min = x / 3; max = x * 4 / 3; vanilla ~ 0.0196D) (blocks per second = x * 100)")
		@Config.RangeDouble(min = 0.0D, max = 0.1D)
		public double swimSpeed = 0.042D;
		@ByteBufHelper.Sync
		@Config.Comment("Depth strider bonus swim speed in percent")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double swimSpeedDepthStrider = 0.1D;
		@ByteBufHelper.Sync
		@Config.Comment("Lower swim speed limit in percent.")
		@Config.RangeDouble(min = 0.0D, max = 1.0D)
		public double swimSpeedLimitLower = 0.3333D;
		@ByteBufHelper.Sync
		@Config.Comment("Upper swim speed limit in percent.")
		@Config.RangeDouble(min = 1.0D, max = 2.0D)
		public double swimSpeedLimitUpper = 1.3333D;
	}

	public static class Client {
		@Config.LangKey("config.gui_oxygen")
		@Config.Comment("")
		public GuiConfig guiOxygenConfig = new GuiConfig(true, 4, -114, -2);
		@Config.LangKey("config.gui_seamoth")
		@Config.Comment("")
		public GuiConfig guiSeamothConfig = new GuiConfig(true, 3, -1, -1);
		@Config.LangKey("config.fog_settings")
		@Config.Comment("")
		public Fog fogSettings = new Fog();

		@Config.Comment("Currently unused. Enable/Disable the custom player model when diving.")
		public boolean customPlayerModel = true;
		@Config.Comment("0: oxygen gui always enabled, 1: oxygen gui enabled while in water, 2: oxygen gui enabled while under water, 3: oxygen gui enabled while underwater or when oxygen is not full")
		@Config.RangeInt(min = 0, max = 3)
		public int guiOxygen = 3;
		@Config.Comment("Enable/Disable a more minecraft looking oxygen gui. It's recommended to set oxygen gui offsetX to 61 and offsetY to -34.")
		public boolean guiOxygenAlternative = false;
		@Config.Comment("Hides the water overlay when wearing a diving gear helmet or when inside a vehicle.")
		public boolean hideWaterOverlay = true;
		@Config.Comment("Enable/Disable underwater ambient sounds.")
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

	public static class Entities {
		@Config.LangKey("config.peeper")
		@Config.Comment("")
		public EntityConfig peeper = new EntityConfig(true, 16, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.bladderfish")
		@Config.Comment("")
		public EntityConfig bladderfish = new EntityConfig(true, 12, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.garryfish")
		@Config.Comment("")
		public EntityConfig garryfish = new EntityConfig(true, 8, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.holefish")
		@Config.Comment("")
		public EntityConfig holefish = new EntityConfig(true, 8, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });
		@Config.LangKey("config.boomerang")
		@Config.Comment("")
		public EntityConfig boomerang = new EntityConfig(true, 8, false, new String[] { "OCEAN" }, new String[] { "ocean", "deep_ocean" }, new int[] { 0 });

		@Config.Comment("For every chunk it chooses a random position every x ticks and then tries to spawn a random entity.")
		@Config.RangeInt(min = 1, max = 1000000)
		public int chance = 1200;
		@Config.Comment("Used to check how many fish are in range to determine whether to spawn more fish.")
		@Config.RangeInt(min = 1, max = 100)
		public int range = 16;
		@Config.Comment("Only spawns more fish when there are less than x fish in range.")
		@Config.RangeInt(min = 0, max = 100)
		public int limit = 2;
	}

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(BetterDiving.MOD_ID)) {
				ConfigManager.sync(BetterDiving.MOD_ID, Config.Type.INSTANCE);
				Minecraft mc = Minecraft.getMinecraft();
				ByteBufHelper.copy(BetterDivingConfig.MASTER_CONFIG, BetterDivingConfig.SLAVE_CONFIG, mc.world == null || mc.isSingleplayer());
			}
		}

		@SubscribeEvent
		public static void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
			if (!event.player.world.isRemote) {
				EntityPlayer player = event.player;

				BetterDiving.network.sendTo(new SPacketSyncConfig(), (EntityPlayerMP) player);
			}
		}

	}

}
