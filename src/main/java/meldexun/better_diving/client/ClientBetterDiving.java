package meldexun.better_diving.client;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.renderer.entity.RenderSeamoth;
import meldexun.better_diving.init.BetterDivingEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientBetterDiving {

	@SuppressWarnings("resource")
	public static PlayerEntity getPlayer() {
		return Minecraft.getInstance().player;
	}

	@SuppressWarnings("resource")
	public static World getWorld() {
		return Minecraft.getInstance().world;
	}

	@SubscribeEvent
	public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(BetterDivingEntities.SEAMOTH.get(), RenderSeamoth::new);
	}

}
