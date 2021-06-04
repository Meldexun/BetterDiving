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

	private static final ReflectionField<Integer> CLIENT_PLAYER_ENTITY_COUNTER_IN_WATER = new ReflectionField<>(ClientPlayerEntity.class, "?", "counterInWater");

	public static float getWaterBrightness(ClientPlayerEntity player) {
		if (!player.areEyesInFluid(FluidTags.WATER)) {
			return 0.0F;
		}
		Minecraft mc = Minecraft.getInstance();
		return MathHelper.clamp((CLIENT_PLAYER_ENTITY_COUNTER_IN_WATER.get(player) + mc.getRenderPartialTicks()) / 60.0F, 0.0F, 1.0F);
	}

	public static float getLightmapBrightness() {
		Minecraft mc = Minecraft.getInstance();
		float partialTicks = mc.getRenderPartialTicks();
		float f = mc.world.getSunBrightness(partialTicks);
		f = MathHelper.clamp((f - 0.2F) / 0.8F, 0.0F, 1.0F);
		return f * 0.2F + 0.1F;
	}

	private static final ReflectionField<Long> FOG_RENDERER_WATER_FOG_UPDATE_TIME = new ReflectionField<>(FogRenderer.class, "?", "waterFogUpdateTime");
	private static final ReflectionField<Float> FOG_RENDERER_RED = new ReflectionField<>(FogRenderer.class, "?", "red");
	private static final ReflectionField<Float> FOG_RENDERER_GREEN = new ReflectionField<>(FogRenderer.class, "?", "green");
	private static final ReflectionField<Float> FOG_RENDERER_BLUE = new ReflectionField<>(FogRenderer.class, "?", "blue");

	private static float[] fogColor = new float[3];
	private static float[] fogColorTarget = new float[3];

	public static void updateFogColor(ActiveRenderInfo activeRenderInfo) {
		Minecraft mc = Minecraft.getInstance();
		long time = Util.milliTime();
		int color = mc.world.getBiome(new BlockPos(activeRenderInfo.getProjectedView())).getWaterFogColor();
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		float f = Math.max(red, Math.max(green, blue));
		f = 1.0F / f;
		red *= f;
		green *= f;
		blue *= f;

		if (FOG_RENDERER_WATER_FOG_UPDATE_TIME.get(null) < 0L) {
			fogColor[0] = red;
			fogColor[1] = green;
			fogColor[2] = blue;
			fogColorTarget[0] = red;
			fogColorTarget[1] = green;
			fogColorTarget[2] = blue;
			FOG_RENDERER_WATER_FOG_UPDATE_TIME.set(null, time);
		}

		float f1 = MathHelper.clamp((float) (time - FOG_RENDERER_WATER_FOG_UPDATE_TIME.get(null)) / 3000.0F, 0.0F, 1.0F);
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
			FOG_RENDERER_WATER_FOG_UPDATE_TIME.set(null, time);
		}

		FOG_RENDERER_RED.set(null, red1);
		FOG_RENDERER_GREEN.set(null, green1);
		FOG_RENDERER_BLUE.set(null, blue1);
	}

}
