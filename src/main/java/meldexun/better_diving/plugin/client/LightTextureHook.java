package meldexun.better_diving.plugin.client;

import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;

public class LightTextureHook {

	public static float getLightmapBrightness() {
		Minecraft mc = Minecraft.getInstance();
		float partialTicks = mc.getFrameTime();
		FluidState fluidState = mc.gameRenderer.mainCamera.getFluidInCamera();

		if (!fluidState.is(FluidTags.WATER)) {
			return 0.0F;
		}

		float f = mc.level.getSkyDarken(partialTicks);
		f = MathHelper.clamp((f - 0.2F) / 0.8F, 0.0F, 1.0F);
		BetterDivingConfig.ServerConfig.UnderwaterVisuals config = BetterDivingConfig.SERVER_CONFIG.underwaterVisuals;
		return (float) MathHelper.lerp(f, config.brightnessNight.get(), config.brightnessDay.get());
	}

}
