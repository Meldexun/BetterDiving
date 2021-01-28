package meldexun.better_diving.event;

import java.util.List;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.oxygenprovider.OxygenProviderManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class ServerEventHandler {

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void onServerStartedEvent(FMLServerStartingEvent event) {
		OxygenProviderManager.getInstance().loadOxygenProviderEntities((List<String>) BetterDivingConfig.SERVER_CONFIG.oxygenProviderEntities.get());
		OxygenProviderManager.getInstance().loadOxygenProviderItems((List<String>) BetterDivingConfig.SERVER_CONFIG.oxygenProviderItems.get());
	}

}
