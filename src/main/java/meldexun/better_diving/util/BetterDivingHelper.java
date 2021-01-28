package meldexun.better_diving.util;

import meldexun.better_diving.api.event.PlayerSwimSpeedEvent;
import meldexun.better_diving.api.event.PlayerWaterBreathingEvent;
import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;

public class BetterDivingHelper {

	public static double getSwimSpeedRespectEquipment(PlayerEntity player) {
		double swimSpeed = BetterDivingConfig.SERVER_CONFIG.movement.baseSwimSpeed.get() * player.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue();

		PlayerSwimSpeedEvent event = new PlayerSwimSpeedEvent(player, swimSpeed);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getNewSwimSpeed();
	}

	public static boolean canBreathUnderwater(PlayerEntity player) {
		if (player.isCreative()) {
			return true;
		}
		if (player.canBreatheUnderwater()) {
			return true;
		}
		if (player.isPotionActive(Effects.WATER_BREATHING)) {
			return true;
		}

		PlayerWaterBreathingEvent event = new PlayerWaterBreathingEvent(player, false);
		MinecraftForge.EVENT_BUS.post(event);
		return event.hasWaterBreathing();
	}

	public static int blocksUnderWater(Entity entity) {
		return blocksUnderWater(entity.world, new BlockPos(entity.getPosX(), entity.getPosY() + entity.getEyeHeight(), entity.getPosZ()));
	}

	public static int blocksUnderWater(World world, BlockPos pos) {
		int i = 0;
		if (world.getBlockState(pos).getMaterial() == Material.WATER) {
			while (world.getBlockState(pos.up(i)).getMaterial() != Material.AIR) {
				i++;
			}
		}
		return i;
	}

	public static Vector3d getMoveVec(double forward, double up, double strafe, double speed, double yaw, double pitch) {
		double d = forward * forward + up * up + strafe * strafe;
		if (d >= 1.0E-4D) {
			if (forward == 0.0D) {
				pitch = 0.0D;
			}
			if (forward < 0.0D) {
				yaw += 180.0D;
				pitch = -pitch;
			}
			if (strafe != 0.0D) {
				double d1 = forward < 0.0D ? 90.0D : -90.0D;
				yaw += strafe / (Math.abs(forward) + Math.abs(strafe)) * d1;
			}
			if (up != 0.0D) {
				double d1 = (up < 0.0D ? 90.0D : -90.0D) - pitch;
				pitch += Math.abs(Math.toDegrees(Math.atan2(up, Math.sqrt(forward * forward + strafe * strafe)))) / 90.0D * d1;
			}
			double d1 = Math.cos(Math.toRadians(-yaw - 180.0D));
			double d2 = Math.sin(Math.toRadians(-yaw - 180.0D));
			double d3 = -Math.cos(Math.toRadians(-pitch));
			double d4 = Math.sin(Math.toRadians(-pitch));
			return new Vector3d(d2 * d3 * speed, d4 * speed, d1 * d3 * speed);
		}
		return Vector3d.ZERO;
	}

}
