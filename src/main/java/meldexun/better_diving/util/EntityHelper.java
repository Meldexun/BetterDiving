package meldexun.better_diving.util;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityHelper {

	public static int blocksUnderWater(Entity entity) {
		return blocksUnderWater(entity.world, new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
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
		return blocksToSeafloor(entity.world, new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
	}

	public static int blocksToSeafloor(World world, BlockPos pos) {
		int i = 0;
		while (world.getBlockState(pos.down(i)).getMaterial() == Material.WATER) {
			i++;
		}
		return i;
	}

}
