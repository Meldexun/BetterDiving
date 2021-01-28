package meldexun.better_diving.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygen;
import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygenProvider;
import meldexun.better_diving.capability.oxygen.entity.player.CapabilityOxygenPlayer;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItem;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItemProvider;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.oxygenprovider.OxygenProviderEntity;
import meldexun.better_diving.oxygenprovider.OxygenProviderItem;
import meldexun.better_diving.oxygenprovider.OxygenProviderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class CapabilityEventHandler {

	@SubscribeEvent
	public static void onAttachEntityCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof PlayerEntity) {
			event.addCapability(CapabilityOxygenProvider.REGISTRY_NAME, new CapabilityOxygenProvider(() -> new CapabilityOxygenPlayer((PlayerEntity) entity, BetterDivingConfig.SERVER_CONFIG.oxygen.oxygenCapacity.get())));
		} else {
			OxygenProviderManager oxygenProviderManager = OxygenProviderManager.getInstance();
			OxygenProviderEntity oxygenProviderEntity = oxygenProviderManager.getOxygenProviderEntity(entity);
			if (oxygenProviderEntity != null) {
				event.addCapability(CapabilityOxygenProvider.REGISTRY_NAME, new CapabilityOxygenProvider(() -> new CapabilityOxygen(oxygenProviderEntity.getOxygenCapacity())));
			}
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesItemStackEvent(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		OxygenProviderManager oxygenProviderManager = OxygenProviderManager.getInstance();
		OxygenProviderItem oxygenProviderItem = oxygenProviderManager.getOxygenProviderItem(stack);
		if (oxygenProviderItem != null) {
			event.addCapability(CapabilityOxygenItemProvider.REGISTRY_NAME, new CapabilityOxygenItemProvider(() -> new CapabilityOxygenItem(stack, oxygenProviderItem.getOxygenCapacity(), oxygenProviderItem.getEquipmentSlots(), oxygenProviderItem.getMaxDivingDepth())));
		}
	}

}
