package meldexun.better_diving.client;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.model.entity.ModelBladderfish;
import meldexun.better_diving.client.model.entity.ModelBoomerang;
import meldexun.better_diving.client.model.entity.ModelGarryfish;
import meldexun.better_diving.client.model.entity.ModelHolefish;
import meldexun.better_diving.client.model.entity.ModelPeeper;
import meldexun.better_diving.client.renderer.entity.RenderFish;
import meldexun.better_diving.client.renderer.entity.RenderSeamoth;
import meldexun.better_diving.entity.EntityBladderfish;
import meldexun.better_diving.entity.EntityBoomerang;
import meldexun.better_diving.entity.EntityGarryfish;
import meldexun.better_diving.entity.EntityHolefish;
import meldexun.better_diving.entity.EntityPeeper;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class EntityModels {

	private EntityModels() {

	}

	@SubscribeEvent
	public static void registerEntityRenders(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntitySeamoth.class, RenderSeamoth::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPeeper.class, manager -> new RenderFish(manager, new ModelPeeper(), 0.4F, "peeper"));
		RenderingRegistry.registerEntityRenderingHandler(EntityBladderfish.class, manager -> new RenderFish(manager, new ModelBladderfish(), 0.4F, "bladderfish"));
		RenderingRegistry.registerEntityRenderingHandler(EntityGarryfish.class, manager -> new RenderFish(manager, new ModelGarryfish(), 0.4F, "garryfish"));
		RenderingRegistry.registerEntityRenderingHandler(EntityHolefish.class, manager -> new RenderFish(manager, new ModelHolefish(), 0.4F, "holefish"));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, manager -> new RenderFish(manager, new ModelBoomerang(), 0.4F, "boomerang"));
	}

}
