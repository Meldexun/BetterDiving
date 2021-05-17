package meldexun.better_diving.util;

import meldexun.better_diving.api.event.PlayerCanBreathEvent;
import meldexun.better_diving.api.event.PlayerSwimSpeedEvent;
import meldexun.better_diving.config.BetterDivingConfig;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectUtils;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;

public class BetterDivingHelper {

	public static double getSwimSpeedRespectEquipment(PlayerEntity player) {
		double swimSpeed = BetterDivingConfig.SERVER_CONFIG.movement.baseSwimSpeed.get();
		swimSpeed *= player.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue();

		PlayerSwimSpeedEvent event = new PlayerSwimSpeedEvent(player, swimSpeed);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getNewSwimSpeed();
	}

	public static boolean canBreath(PlayerEntity player) {
		boolean canBreath = !player.areEyesInFluid(FluidTags.WATER);
		canBreath |= player.world.getBlockState(player.getPosition()).matchesBlock(Blocks.BUBBLE_COLUMN);
		canBreath |= player.isCreative();
		canBreath |= player.canBreatheUnderwater();
		canBreath |= EffectUtils.canBreatheUnderwater(player);

		PlayerCanBreathEvent event = new PlayerCanBreathEvent(player, canBreath);
		MinecraftForge.EVENT_BUS.post(event);
		return event.canBreath();
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
		if (d >= 1.0E-7D) {
			Vector3d vecForward = fromPitchYaw(forward < 0.0D ? -pitch : pitch, forward < 0.0D ? yaw + 180.0D : yaw).scale(Math.abs(forward));
			Vector3d vecUp = fromPitchYaw((up < 0.0D ? 1.0D : -1.0D) * (90.0D + ((pitch + 90.0D) % 180.0D == 0.0D ? -0.0078125D : 0.0D)), yaw).scale(Math.abs(up));
			Vector3d vecStrafe = fromPitchYaw(0.0D, yaw + (strafe < 0.0D ? 90.0D : -90.0D)).scale(Math.abs(strafe));
			double d1 = d < 1.0D ? Math.sqrt(d) : 1.0D;
			return vecForward.add(vecUp).add(vecStrafe).normalize().scale(d1 * speed);
		}
		return Vector3d.ZERO;
	}

	public static Vector3d getSeamothMoveVec(double forward, double up, double strafe, double speed, double yaw, double pitch) {
		double d = forward * forward + up * up + strafe * strafe;
		if (d >= 1.0E-7D) {
			Vector3d vecForward = fromPitchYaw(forward < 0.0D ? -pitch : pitch, forward < 0.0D ? yaw + 180.0D : yaw).scale(Math.abs(forward));
			Vector3d vecUp = fromPitchYaw(pitch + (up < 0.0D ? 90.0D : -90.0D), yaw).scale(Math.abs(up));
			Vector3d vecStrafe = fromPitchYaw(0.0D, yaw + (strafe < 0.0D ? 90.0D : -90.0D)).scale(Math.abs(strafe));
			double d1 = d < 1.0D ? Math.sqrt(d) : 1.0D;
			return vecForward.add(vecUp).add(vecStrafe).normalize().scale(d1 * speed);
		}
		return Vector3d.ZERO;
	}

	private static Vector3d fromPitchYaw(double pitch, double yaw) {
		double d1 = Math.cos(Math.toRadians(-yaw - 180.0D));
		double d2 = Math.sin(Math.toRadians(-yaw - 180.0D));
		double d3 = -Math.cos(Math.toRadians(-pitch));
		double d4 = Math.sin(Math.toRadians(-pitch));
		return new Vector3d(d2 * d3, d4, d1 * d3);
	}

}
