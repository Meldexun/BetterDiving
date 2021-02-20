package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.inventory.CapabilityItemHandlerProvider;
import meldexun.better_diving.capability.inventory.item.CapabilityItemHandlerItemProvider;
import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygen;
import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygenProvider;
import meldexun.better_diving.capability.oxygen.entity.player.CapabilityOxygenPlayer;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItem;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItemProvider;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.oxygenprovider.DivingGearManager;
import meldexun.better_diving.oxygenprovider.OxygenProviderEntity;
import meldexun.better_diving.oxygenprovider.OxygenProviderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class CapabilityEventHandler {

	@SubscribeEvent
	public static void onAttachEntityCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof PlayerEntity) {
			event.addCapability(CapabilityOxygenProvider.REGISTRY_NAME, new CapabilityOxygenProvider(() -> new CapabilityOxygenPlayer((PlayerEntity) entity, BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenCapacity.get())));
		} else {
			OxygenProviderEntity oxygenProviderEntity = DivingGearManager.getOxygenProviderEntity(entity);
			if (oxygenProviderEntity != null) {
				event.addCapability(CapabilityOxygenProvider.REGISTRY_NAME, new CapabilityOxygenProvider(() -> new CapabilityOxygen(oxygenProviderEntity.oxygenCapacity)));
			}
		}
		if (entity instanceof EntitySeamoth) {
			event.addCapability(CapabilityItemHandlerItemProvider.REGISTRY_NAME, new CapabilityItemHandlerProvider(() -> new ItemStackHandler(1)));
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesItemStackEvent(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		OxygenProviderItem oxygenProviderItem = DivingGearManager.getOxygenProviderItem(stack);
		if (oxygenProviderItem != null) {
			event.addCapability(CapabilityOxygenItemProvider.REGISTRY_NAME, new CapabilityOxygenItemProvider(() -> new CapabilityOxygenItem(stack, oxygenProviderItem.oxygenCapacity, oxygenProviderItem.slots, oxygenProviderItem.needsDivingMask)));
		}
	}

}
