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
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class EntityModels {

	@SubscribeEvent
	public static void registerEntityRenders(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntitySeamoth.class, new IRenderFactory<EntitySeamoth>() {
			@Override
			public Render<? super EntitySeamoth> createRenderFor(RenderManager manager) {
				return new RenderSeamoth(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityPeeper.class, new IRenderFactory<EntityPeeper>() {
			@Override
			public Render<? super EntityPeeper> createRenderFor(RenderManager manager) {
				return new RenderFish(manager, new ModelPeeper(), 0.4F, "peeper");
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityBladderfish.class, new IRenderFactory<EntityBladderfish>() {
			@Override
			public Render<? super EntityBladderfish> createRenderFor(RenderManager manager) {
				return new RenderFish(manager, new ModelBladderfish(), 0.4F, "bladderfish");
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityGarryfish.class, new IRenderFactory<EntityGarryfish>() {
			@Override
			public Render<? super EntityGarryfish> createRenderFor(RenderManager manager) {
				return new RenderFish(manager, new ModelGarryfish(), 0.4F, "garryfish");
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityHolefish.class, new IRenderFactory<EntityHolefish>() {
			@Override
			public Render<? super EntityHolefish> createRenderFor(RenderManager manager) {
				return new RenderFish(manager, new ModelHolefish(), 0.4F, "holefish");
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new IRenderFactory<EntityBoomerang>() {
			@Override
			public Render<? super EntityBoomerang> createRenderFor(RenderManager manager) {
				return new RenderFish(manager, new ModelBoomerang(), 0.4F, "boomerang");
			}
		});
	}

}
