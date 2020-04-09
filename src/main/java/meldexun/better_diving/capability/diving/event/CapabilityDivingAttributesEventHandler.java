package meldexun.better_diving.capability.diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.integration.IndustrialCraft;
import meldexun.better_diving.network.packet.SPacketSyncOxygen;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

@EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class CapabilityDivingAttributesEventHandler {

	private CapabilityDivingAttributesEventHandler() {

	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPlayerBreakSpeedEvent(BreakSpeed event) {
		EntityPlayer player = event.getEntityPlayer();

		if (BetterDivingConfig.getInstance().modules.blockBreaking && player.isInWater() && !IndustrialCraft.isDrilling(player)) {
			ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

			event.setNewSpeed(event.getNewSpeed() * idiving.getBreakSpeedFromPlayer());
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote) {
			EntityPlayer player = event.player;

			BetterDiving.network.sendTo(new SPacketSyncOxygen(player), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawnEvent(PlayerRespawnEvent event) {
		if (!event.player.world.isRemote) {
			EntityPlayer player = event.player;

			BetterDiving.network.sendTo(new SPacketSyncOxygen(player), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimensionEvent(PlayerChangedDimensionEvent event) {
		if (!event.player.world.isRemote) {
			EntityPlayer player = event.player;

			BetterDiving.network.sendTo(new SPacketSyncOxygen(player), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		if (event.phase == Phase.START) {
			EntityPlayer player = event.player;

			if (!player.world.isRemote || !(player instanceof EntityOtherPlayerMP)) {
				player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).tick();
			}
		}
	}

}
