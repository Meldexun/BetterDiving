package meldexun.better_diving.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.ClientWorld.ClientWorldInfo;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("deprecation")
@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

	@Unique
	private static float moonR;
	@Unique
	private static float moonG;
	@Unique
	private static float moonB;
	@Unique
	private static float moonA;

	@Unique
	private static double seaLevel() {
		double seaLevel;
		if (BetterDivingConfig.CLIENT_CONFIG.seaLevelOverride.get()) {
			seaLevel = BetterDivingConfig.CLIENT_CONFIG.seaLevel.get();
		} else {
			Minecraft mc = Minecraft.getInstance();
			seaLevel = mc.level.getSeaLevel();
		}
		return seaLevel - (1.0D - Fluids.WATER.defaultFluidState().getOwnHeight());
	}

	@Unique
	private static double maxSunMoonAngle() {
		Minecraft mc = Minecraft.getInstance();
		double d1 = (mc.options.renderDistance - 1) * 16.0D;
		double d2 = seaLevel() - mc.gameRenderer.getMainCamera().getPosition().y();
		return Math.toDegrees(Math.atan2(d1, d2) - Math.atan2(7.5D, 100.0D));
	}

	@Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;color3f(FFF)V", ordinal = 0))
	public void setupSkyColor(float r, float g, float b) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameRenderer.getMainCamera().getPosition().y() < seaLevel()
				&& (BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingBelowSealevel.get()
				|| BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingUnderwater.get()
				&& mc.gameRenderer.getMainCamera().getFluidInCamera().is(FluidTags.WATER))) {
			double fog = MathHelper.clamp(0.0625D * (seaLevel() - mc.gameRenderer.getMainCamera().getPosition().y()), 0.0D, 1.0D);
			double sky = 1.0D - fog;
			RenderSystem.color3f(
					(float) (r * sky + FogRenderer.fogRed * fog),
					(float) (g * sky + FogRenderer.fogGreen * fog),
					(float) (b * sky + FogRenderer.fogBlue * fog));
		} else {
			RenderSystem.color3f(r, g, b);
		}
	}

	@Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/DimensionRenderInfo;getSunriseColor(FF)[F"))
	public float[] getSunriseColor(DimensionRenderInfo dimensionRenderInfo, float timeOfDay, float frameTime) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameRenderer.getMainCamera().getPosition().y() < seaLevel()
				&& (BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingBelowSealevel.get()
				|| BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingUnderwater.get()
				&& mc.gameRenderer.getMainCamera().getFluidInCamera().is(FluidTags.WATER))) {
			return null;
		}
		return dimensionRenderInfo.getSunriseColor(timeOfDay, frameTime);
	}

	@Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V", ordinal = 0))
	public void setupSunColor(float r, float g, float b, float a) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameRenderer.getMainCamera().getPosition().y() < seaLevel()
				&& (BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingBelowSealevel.get()
				|| BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingUnderwater.get()
				&& mc.gameRenderer.getMainCamera().getFluidInCamera().is(FluidTags.WATER))) {
			double maxAngle = maxSunMoonAngle();
			double angle = mc.level.getTimeOfDay(mc.getFrameTime()) * 360.0D;
			double alpha = MathHelper.clamp(0.1D * Math.max(maxAngle - angle, maxAngle - (360.0D - angle)), 0.0D, 1.0D);
			RenderSystem.color4f(r, g, b, (float) (a * alpha));
		} else {
			RenderSystem.color4f(r, g, b, a);
		}
		moonR = r;
		moonG = g;
		moonB = b;
		moonA = a;
	}

	@Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BufferBuilder;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V", ordinal = 2))
	public void setupMoonColor(MatrixStack matrixStack, float frameTime, CallbackInfo info) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameRenderer.getMainCamera().getPosition().y() < seaLevel()
				&& (BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingBelowSealevel.get()
				|| BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingUnderwater.get()
				&& mc.gameRenderer.getMainCamera().getFluidInCamera().is(FluidTags.WATER))) {
			double maxAngle = maxSunMoonAngle();
			double angle = mc.level.getTimeOfDay(mc.getFrameTime()) * 360.0D;
			angle = (angle + 180.0D) % 360.0D;
			double alpha = MathHelper.clamp(0.1D * Math.max(maxAngle - angle, maxAngle - (360.0D - angle)), 0.0D, 1.0D);
			RenderSystem.color4f(moonR, moonG, moonB, (float) (moonA * alpha));
		} else {
			RenderSystem.color4f(moonR, moonG, moonB, moonA);
		}
	}

	@Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getStarBrightness(F)F"))
	public float getStarBrightness(ClientWorld clientWorld, float frameTime) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameRenderer.getMainCamera().getPosition().y() < seaLevel()
				&& (BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingBelowSealevel.get()
				|| BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingUnderwater.get()
				&& mc.gameRenderer.getMainCamera().getFluidInCamera().is(FluidTags.WATER))) {
			return 0.0F;
		}
		return clientWorld.getStarBrightness(frameTime);
	}

	@Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld$ClientWorldInfo;getHorizonHeight()D"))
	public double getHorizonHeight(ClientWorldInfo clientWorldInfo) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameRenderer.getMainCamera().getPosition().y() < seaLevel()
				&& (BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingBelowSealevel.get()
				|| BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingUnderwater.get()
				&& mc.gameRenderer.getMainCamera().getFluidInCamera().is(FluidTags.WATER))) {
			return Double.MIN_VALUE;
		}
		return clientWorldInfo.getHorizonHeight();
	}

	@Inject(method = "renderClouds", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/DimensionRenderInfo;getCloudHeight()F"))
	public void renderClouds(MatrixStack matrixStack, float frameTime, double x, double y, double z,
			CallbackInfo info) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameRenderer.getMainCamera().getPosition().y() < seaLevel()
				&& (BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingBelowSealevel.get()
				|| BetterDivingConfig.CLIENT_CONFIG.skipSkyRenderingUnderwater.get()
				&& mc.gameRenderer.getMainCamera().getFluidInCamera().is(FluidTags.WATER))) {
			info.cancel();
		}
	}

}
