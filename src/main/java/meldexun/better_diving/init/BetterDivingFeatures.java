package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.world.gen.feature.OceanOreFeatureConfig;
import meldexun.better_diving.world.gen.feature.RavineOutcropFeature;
import meldexun.better_diving.world.gen.feature.SeafloorOutcropFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BetterDivingFeatures {

	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BetterDiving.MOD_ID);

	public static final RegistryObject<SeafloorOutcropFeature> OCEAN_FLOOR = FEATURES.register("ocean_floor", () -> new SeafloorOutcropFeature(OceanOreFeatureConfig.CODEC));
	public static final RegistryObject<RavineOutcropFeature> OCEAN_RAVINE = FEATURES.register("ocean_ravine", () -> new RavineOutcropFeature(OceanOreFeatureConfig.CODEC));

	public static void registerFeatures() {
		FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
