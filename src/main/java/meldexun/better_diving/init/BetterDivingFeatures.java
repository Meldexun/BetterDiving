package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.world.gen.feature.RavineOutcropFeature;
import meldexun.better_diving.world.gen.feature.SeafloorOutcropFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BetterDivingFeatures {

	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BetterDiving.MOD_ID);

	public static final RegistryObject<SeafloorOutcropFeature> LIMESTONE_OUTCROP = FEATURES.register("limestone_outcrop", () -> new SeafloorOutcropFeature(FeatureSpreadConfig.CODEC, BetterDivingBlocks.LIMESTONE_OUTCROP.get()));
	public static final RegistryObject<SeafloorOutcropFeature> SANDSTONE_OUTCROP = FEATURES.register("sandstone_outcrop", () -> new SeafloorOutcropFeature(FeatureSpreadConfig.CODEC, BetterDivingBlocks.SANDSTONE_OUTCROP.get()));
	public static final RegistryObject<RavineOutcropFeature> SHALE_OUTCROP = FEATURES.register("shale_outcrop", () -> new RavineOutcropFeature(FeatureSpreadConfig.CODEC, BetterDivingBlocks.SHALE_OUTCROP.get()));

	public static void registerFeatures() {
		FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
