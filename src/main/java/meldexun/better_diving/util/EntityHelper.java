package meldexun.better_diving.util;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHelper {

	private EntityHelper() {

	}

	public static int blocksUnderWater(Entity entity) {
		return EntityHelper.blocksUnderWater(entity.world, new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
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

	public static int blocksToSeafloor(Entity entity) {
		return EntityHelper.blocksToSeafloor(entity.world, new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
	}

	public static int blocksToSeafloor(World world, BlockPos pos) {
		int i = 0;
		while (world.getBlockState(pos.down(i)).getMaterial() == Material.WATER) {
			i++;
		}
		return i - 1;
	}

	public static void resetPlayerSize(EntityPlayer player) {
		EntityHelper.updatePlayerSize(player, 1.8F, 0.6F, 1.62F);
	}

	public static void updatePlayerSize(EntityPlayer player, float height, float width, float eyeHeight) {
		if (player.eyeHeight != eyeHeight) {
			player.eyeHeight = eyeHeight;
		}
		if (player.height != height || player.width != width) {
			player.height = height;
			player.width = width;
			double d = (double) width * 0.5D;
			Vec3d vec1 = player.getPositionVector().subtract(d, 0.0D, d);
			Vec3d vec2 = player.getPositionVector().add(d, height, d);
			player.setEntityBoundingBox(new AxisAlignedBB(vec1.x, vec1.y, vec1.z, vec2.x, vec2.y, vec2.z));
		}
	}

}
