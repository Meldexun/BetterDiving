package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.api.event.PlayerCanBreathEvent;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.oxygenprovider.DivingGearManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class ServerEventHandler {

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onEntitySizeEvent(EntityEvent.Size event) {
		if (event.getEntity() instanceof PlayerEntity && event.getEntity().getRidingEntity() instanceof EntitySeamoth) {
			event.setNewEyeHeight(event.getNewEyeHeight() * 1.164375F / 1.62F);
		}
	}

	@SubscribeEvent
	public static void onServerStartedEvent(FMLServerStartingEvent event) {
		DivingGearManager.reloadConfigs();
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPlayerWaterBreathingEvent(PlayerCanBreathEvent event) {
		if (event.getPlayer().getRidingEntity() instanceof EntitySeamoth && ((EntitySeamoth) event.getPlayer().getRidingEntity()).hasEnergy()) {
			event.setCanBreath(true);
		}
	}

}
