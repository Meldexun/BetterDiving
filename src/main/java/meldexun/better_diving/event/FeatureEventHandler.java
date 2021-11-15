package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.config.BetterDivingConfig.ServerConfig.Ores.OreConfig;
import meldexun.better_diving.init.BetterDivingFeatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class FeatureEventHandler {

	public static ConfiguredFeature<FeatureSpreadConfig, ?> limestoneOutcrop;
	public static ConfiguredFeature<FeatureSpreadConfig, ?> sandstoneOutcrop;
	public static ConfiguredFeature<FeatureSpreadConfig, ?> shaleOutcrop;

	private static ConfiguredFeature<FeatureSpreadConfig, ?> register(String name, Feature<FeatureSpreadConfig> feature, OreConfig config) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BetterDiving.MOD_ID, name), feature.configured(new FeatureSpreadConfig(FeatureSpread.of(config.base.get(), config.spread.get()))));
	}

	public static void registerConfiguredFeatures() {
		limestoneOutcrop = register("limestone_outcrop", BetterDivingFeatures.LIMESTONE_OUTCROP.get(), BetterDivingConfig.SERVER_CONFIG.ores.limestone);
		sandstoneOutcrop = register("sandstone_outcrop", BetterDivingFeatures.SANDSTONE_OUTCROP.get(), BetterDivingConfig.SERVER_CONFIG.ores.sandstone);
		shaleOutcrop = register("shalestone_outcrop", BetterDivingFeatures.SHALE_OUTCROP.get(), BetterDivingConfig.SERVER_CONFIG.ores.shale);
	}

	@SubscribeEvent
	public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
		if (event.getCategory() == Category.OCEAN) {
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, limestoneOutcrop);
			if (event.getDepth() <= -1.5F) {
				event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, sandstoneOutcrop);
			}
			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, shaleOutcrop);
		}
	}

}
