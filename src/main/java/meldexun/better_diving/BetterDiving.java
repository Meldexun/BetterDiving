package meldexun.better_diving;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import meldexun.better_diving.api.BetterDivingModules;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.event.FeatureEventHandler;
import meldexun.better_diving.init.BetterDivingBlocks;
import meldexun.better_diving.init.BetterDivingCapabilities;
import meldexun.better_diving.init.BetterDivingContainers;
import meldexun.better_diving.init.BetterDivingEntities;
import meldexun.better_diving.init.BetterDivingFeatures;
import meldexun.better_diving.init.BetterDivingItems;
import meldexun.better_diving.init.BetterDivingPackets;
import meldexun.better_diving.init.BetterDivingSounds;
import meldexun.better_diving.oxygenprovider.DivingGearManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(BetterDiving.MOD_ID)
public class BetterDiving {

	public static final String MOD_ID = "better_diving";

	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	private static final String NETWORK_VERSION = "1.0.0";
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "main"), () -> NETWORK_VERSION, NETWORK_VERSION::equals, NETWORK_VERSION::equals);

	public BetterDiving() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, BetterDivingConfig.CLIENT_SPEC);
		ModLoadingContext.get().registerConfig(Type.SERVER, BetterDivingConfig.SERVER_SPEC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::updateConfig);
		BetterDivingBlocks.registerBlocks();
		BetterDivingItems.registerItems();
		BetterDivingEntities.registerEntities();
		BetterDivingContainers.registerContainers();
		BetterDivingSounds.registerSounds();
		BetterDivingFeatures.registerFeatures();
	}

	private void setup(FMLCommonSetupEvent event) {
		BetterDivingCapabilities.registerCapabilities();
		BetterDivingPackets.registerPackets();
		FeatureEventHandler.registerConfiguredFeatures();
	}

	private void updateConfig(ModConfig.ModConfigEvent event) {
		if (event.getConfig().getModId().equals(MOD_ID)) {
			DivingGearManager.reloadConfigs();
			BetterDivingModules.breakSpeedChanges = BetterDivingConfig.SERVER_CONFIG.breakSpeedChanges.get();
			BetterDivingModules.movementChanges = BetterDivingConfig.SERVER_CONFIG.movementChanges.get();
			BetterDivingModules.oxygenChanges = BetterDivingConfig.SERVER_CONFIG.oxygenChanges.get();
		}
	}

}
