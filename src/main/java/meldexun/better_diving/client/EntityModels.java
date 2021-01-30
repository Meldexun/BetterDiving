package meldexun.better_diving.client;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.renderer.entity.RenderSeamoth;
import meldexun.better_diving.init.BetterDivingEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Dist.CLIENT)
public class EntityModels {

	@SubscribeEvent
	public static void registerEntityRenderers(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(BetterDivingEntities.SEAMOTH.get(), RenderSeamoth::new);
	}

}
