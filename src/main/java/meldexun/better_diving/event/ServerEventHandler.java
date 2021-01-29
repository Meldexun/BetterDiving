package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.oxygenprovider.DivingGearManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class ServerEventHandler {

	@SubscribeEvent
	public static void onServerStartedEvent(FMLServerStartingEvent event) {
		DivingGearManager.reloadConfigs();
	}

}
