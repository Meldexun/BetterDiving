package meldexun.better_diving.util;

import net.minecraft.entity.Entity;

public class MovementHelper {

	public static void move2D(Entity entity, double strafe, double forward, double speed, double yaw) {
		double d = strafe * strafe + forward * forward;
		if (d >= 1.0E-4D) {
			d = Math.sqrt(d);
			if (d < 1.0D) {
				d = 1.0D;
			}
			d = speed / d;

			strafe *= d;
			forward *= d;

			double d1 = Math.sin(yaw * 0.017453292D);
			double d2 = Math.cos(yaw * 0.017453292D);

			entity.motionX += strafe * d2 - forward * d1;
			entity.motionZ += forward * d2 + strafe * d1;
		}
	}

	public static void move3D(Entity entity, double strafe, double up, double forward, double speed, double yaw, double pitch) {
		double d = strafe * strafe + up * up + forward * forward;
		if (d >= 1.0E-4D) {
			d = Math.sqrt(d);
			if (d < 1.0D) {
				d = 1.0D;
			}
			d = speed / d;

			strafe *= d;
			up *= d;
			forward *= d;

			double d1 = Math.sin(yaw * 0.017453292D);
			double d2 = Math.cos(yaw * 0.017453292D);
			double d3 = Math.sin(pitch * 0.017453292D);
			double d4 = Math.cos(pitch * 0.017453292D);

			entity.motionX += strafe * d2 - forward * d1 * d4;
			entity.motionY += up - forward * d3;
			entity.motionZ += forward * d2 * d4 + strafe * d1;
		}
	}

}
