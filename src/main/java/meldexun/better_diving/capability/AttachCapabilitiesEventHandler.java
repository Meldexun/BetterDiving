package meldexun.better_diving.capability;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.inventory.CapabilityItemHandlerProvider;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class AttachCapabilitiesEventHandler {

	@SubscribeEvent
	public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(CapabilityDivingAttributesProvider.LOCATION_DIVING_ATTRIBUTES, CapabilityDivingAttributesProvider.createProvider());
		} else if (event.getObject() instanceof EntitySeamoth) {
			event.addCapability(CapabilityItemHandlerProvider.LOCATION_ITEM_STACK_HANDLER, CapabilityItemHandlerProvider.createProvider(1));
		}
	}

}
