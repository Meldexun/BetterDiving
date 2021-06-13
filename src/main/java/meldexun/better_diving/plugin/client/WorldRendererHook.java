package meldexun.better_diving.plugin.client;

import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;

public class WorldRendererHook {

	public static boolean shouldRenderSky() {
		if (!BetterDivingConfig.CLIENT_CONFIG.skipSkyRendering.get()) {
			return true;
		}
		Minecraft mc = Minecraft.getInstance();
		FluidState fluidState = mc.gameRenderer.mainCamera.getFluidInCamera();
		return !fluidState.is(FluidTags.WATER);
	}

}
