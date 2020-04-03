package meldexun.better_diving.capability.diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.integration.IndustrialCraft;
import meldexun.better_diving.network.packet.SPacketSyncConfigHelper;
import meldexun.better_diving.network.packet.SPacketSyncOxygen;
import meldexun.better_diving.util.BetterDivingConfigClient;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
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

	@SubscribeEvent
	public static void onPlayerEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
		if (event.getEntity() instanceof EntityPlayer && (event.getSlot() == EntityEquipmentSlot.HEAD || event.getSlot() == EntityEquipmentSlot.CHEST || event.getSlot() == EntityEquipmentSlot.LEGS || event.getSlot() == EntityEquipmentSlot.FEET)) {
			EntityPlayer player = (EntityPlayer) event.getEntity();

			player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).changeEquip(player);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPlayerHarvestEvent(BreakSpeed event) {
		if (BetterDivingConfigClient.blockBreaking) {
			EntityPlayer player = event.getEntityPlayer();

			if (player.isInWater() && (!IndustrialCraft.loaded || !IndustrialCraft.isPlayerDrilling(player))) {
				float harvestSpeed = event.getNewSpeed();

				if (player.isInsideOfMaterial(Material.WATER)) {
					harvestSpeed *= player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).getBreakSpeed();
				}

				if (!player.onGround) {
					harvestSpeed *= 5.0F;
				}

				event.setNewSpeed(harvestSpeed);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote) {
			EntityPlayer player = event.player;

			player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).changeEquip(player);
			BetterDiving.CONNECTION.sendTo(new SPacketSyncConfigHelper(), (EntityPlayerMP) player);
			BetterDiving.CONNECTION.sendTo(new SPacketSyncOxygen(player), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawnEvent(PlayerRespawnEvent event) {
		if (!event.player.world.isRemote) {
			EntityPlayer player = event.player;

			player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).changeEquip(player);
			BetterDiving.CONNECTION.sendTo(new SPacketSyncOxygen(player), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangeDimensionEvent(PlayerChangedDimensionEvent event) {
		if (!event.player.world.isRemote) {
			EntityPlayer player = event.player;

			player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).changeEquip(player);
			BetterDiving.CONNECTION.sendTo(new SPacketSyncOxygen(player), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		if (event.phase == Phase.START) {
			EntityPlayer player = event.player;

			player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).tick(player);
		}
	}

}
