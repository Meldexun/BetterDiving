package meldexun.better_diving.event;

import java.util.HashMap;
import java.util.Map;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.util.EntityHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class EventHandler {

	private EventHandler() {

	}

	private static Map<EntityPlayer, Boolean> prevInSeamoth = new HashMap<>();

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

		boolean inSeamoth = player.getRidingEntity() instanceof EntitySeamoth;
		if (inSeamoth) {
			EntityHelper.updatePlayerSize(player, 1.53F, 0.6F, 1.164375F);
		} else if (EventHandler.prevInSeamoth.getOrDefault(player, new Boolean(false)).booleanValue()) {
			EntityHelper.resetPlayerSize(player);
		}
		EventHandler.prevInSeamoth.put(player, inSeamoth);

		if (idiving.isDiving()) {
			player.setSprinting(false);
			player.setSneaking(false);
			EntityHelper.updatePlayerSize(player, 0.6F, 0.6F, 0.4F);
		} else if (idiving.prevIsDiving()) {
			EntityHelper.resetPlayerSize(player);
		}
	}

}
