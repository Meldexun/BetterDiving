package meldexun.better_diving.plugin.client;

import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class MouseHelperHook {

	private static double smoothX;
	private static double smoothY;

	public static boolean onTurnPlayer() {
		Minecraft mc = Minecraft.getInstance();
		return mc.player.getVehicle() instanceof EntitySeamoth;
	}

	public static double onTurnPlayerX(double deltaTime, double sensitivity, double deltaX) {
		Minecraft mc = Minecraft.getInstance();
		if (!(mc.player.getVehicle() instanceof EntitySeamoth)) {
			return 0.0D;
		}
		if (!((EntitySeamoth) mc.player.getVehicle()).hasEnergy()) {
			return 0.0D;
		}
		deltaTime *= 20.0D;
		double dx = MathHelper.clamp(deltaX * sensitivity, -deltaTime * 45.0D, deltaTime * 45.0D);
		double rx = dx * 0.5D;
		smoothX += dx * 0.5D;
		if (Math.abs(smoothX) > 0.01D) {
			rx += smoothX * 0.5F * deltaTime;
			smoothX *= 1.0F - 0.2F * deltaTime;
		} else {
			smoothX = 0.0F;
		}
		return rx;
	}

	public static double onTurnPlayerY(double deltaTime, double sensitivity, double deltaY) {
		Minecraft mc = Minecraft.getInstance();
		if (!(mc.player.getVehicle() instanceof EntitySeamoth)) {
			return 0.0D;
		}
		if (!((EntitySeamoth) mc.player.getVehicle()).hasEnergy()) {
			return 0.0D;
		}
		deltaTime *= 20.0D;
		double dy = MathHelper.clamp(deltaY * sensitivity, -deltaTime * 45.0D, deltaTime * 45.0D);
		double ry = dy * 0.5D;
		smoothY += dy * 0.5D;
		if (Math.abs(smoothY) > 0.01D) {
			ry += smoothY * 0.5F * deltaTime;
			smoothY *= 1.0F - 0.2F * deltaTime;
		} else {
			smoothY = 0.0F;
		}
		return ry;
	}

}
