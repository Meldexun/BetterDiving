package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.block.BlockUnderwaterOre;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.config.BetterDivingConfig.ServerConfig.Ores.OreConfig;
import meldexun.better_diving.init.BetterDivingBlocks;
import meldexun.better_diving.init.BetterDivingFeatures;
import meldexun.better_diving.world.gen.feature.OceanOreFeatureConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class FeatureEventHandler {

	public static ConfiguredFeature<OceanOreFeatureConfig, ?> limestoneOutcrop;
	public static ConfiguredFeature<OceanOreFeatureConfig, ?> sandstoneOutcrop;
	public static ConfiguredFeature<OceanOreFeatureConfig, ?> shaleOutcrop;

	private static ConfiguredFeature<OceanOreFeatureConfig, ?> register(String name, Feature<OceanOreFeatureConfig> feature, BlockUnderwaterOre block, OreConfig config) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BetterDiving.MOD_ID, name), feature.configured(new OceanOreFeatureConfig(block, config)));
	}

	public static void registerConfiguredFeatures() {
		limestoneOutcrop = register("limestone_outcrop", BetterDivingFeatures.OCEAN_FLOOR.get(), BetterDivingBlocks.LIMESTONE_OUTCROP.get(), BetterDivingConfig.SERVER_CONFIG.ores.limestone);
		sandstoneOutcrop = register("sandstone_outcrop", BetterDivingFeatures.OCEAN_FLOOR.get(), BetterDivingBlocks.SANDSTONE_OUTCROP.get(), BetterDivingConfig.SERVER_CONFIG.ores.sandstone);
		shaleOutcrop = register("shalestone_outcrop", BetterDivingFeatures.OCEAN_RAVINE.get(), BetterDivingBlocks.SHALE_OUTCROP.get(), BetterDivingConfig.SERVER_CONFIG.ores.shale);
	}

	@SubscribeEvent
	public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
		// TODO add biome config
		if (event.getCategory() == Category.OCEAN) {
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, limestoneOutcrop);
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, sandstoneOutcrop);
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, shaleOutcrop);
		}
	}

}
