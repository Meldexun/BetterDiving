package meldexun.better_diving.plugin;

import meldexun.better_diving.util.reflection.ReflectionField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Hook {

	public static float getWaterBrightness(ClientPlayerEntity player) {
		if (!player.isEyeInFluid(FluidTags.WATER)) {
			return 0.0F;
		}
		Minecraft mc = Minecraft.getInstance();
		return MathHelper.clamp((player.waterVisionTime + mc.getFrameTime()) / 60.0F, 0.0F, 1.0F);
	}

	public static float getLightmapBrightness() {
		Minecraft mc = Minecraft.getInstance();
		float partialTicks = mc.getFrameTime();
		float f = mc.level.getSkyDarken(partialTicks);
		f = MathHelper.clamp((f - 0.2F) / 0.8F, 0.0F, 1.0F);
		return f * 0.2F + 0.1F;
	}

	private static float[] fogColor = new float[3];
	private static float[] fogColorTarget = new float[3];

	public static void updateFogColor(ActiveRenderInfo activeRenderInfo) {
		Minecraft mc = Minecraft.getInstance();
		long time = Util.getMillis();
		int color = mc.level.getBiome(new BlockPos(activeRenderInfo.getPosition())).getWaterFogColor();
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		float f = Math.max(red, Math.max(green, blue));
		f = 1.0F / f;
		red *= f;
		green *= f;
		blue *= f;

		if (FogRenderer.biomeChangedTime < 0L) {
			fogColor[0] = red;
			fogColor[1] = green;
			fogColor[2] = blue;
			fogColorTarget[0] = red;
			fogColorTarget[1] = green;
			fogColorTarget[2] = blue;
			FogRenderer.biomeChangedTime = time;
		}

		float f1 = MathHelper.clamp((float) (time - FogRenderer.biomeChangedTime) / 3000.0F, 0.0F, 1.0F);
		float red1 = MathHelper.lerp(f1, fogColor[0], fogColorTarget[0]);
		float green1 = MathHelper.lerp(f1, fogColor[1], fogColorTarget[1]);
		float blue1 = MathHelper.lerp(f1, fogColor[2], fogColorTarget[2]);
		
		if (red != fogColorTarget[0] || green != fogColorTarget[1] || blue != fogColorTarget[2]) {
			fogColor[0] = red1;
			fogColor[1] = green1;
			fogColor[2] = blue1;
			fogColorTarget[0] = red;
			fogColorTarget[1] = green;
			fogColorTarget[2] = blue;
			FogRenderer.biomeChangedTime = time;
		}

		FogRenderer.fogRed = red1;
		FogRenderer.fogRed = green1;
		FogRenderer.fogRed = blue1;
	}

}
