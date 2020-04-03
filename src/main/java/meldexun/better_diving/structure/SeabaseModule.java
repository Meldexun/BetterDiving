package meldexun.better_diving.structure;

import meldexun.better_diving.block.BlockStructure;
import meldexun.better_diving.tileentity.TileEntityBuilding;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class SeabaseModule {

	public abstract void generate(World world, BlockPos pos, Rotation rotation);

	public abstract boolean checkPos(World world, BlockPos pos, Rotation rotation);

	public static void setBase(World world, BlockPos pos, BlockStructure block) {
		if (!world.isRemote) {
			world.setBlockState(pos, block.getDefaultState(), 3);
			TileEntityBuilding tileEntity = (TileEntityBuilding) world.getTileEntity(pos);
			tileEntity.basePos = pos;
			tileEntity.isBase = true;
			tileEntity.isDoor = false;
		}
	}

	public static void setBlocks(World world, BlockPos basePos, BlockPos startPos, BlockPos endPos, Rotation rotation, BlockStructure block, boolean isDoor) {
		if (!world.isRemote) {
			startPos = startPos.rotate(rotation);
			endPos = endPos.rotate(rotation);
			BlockPos pos1 = new BlockPos(Math.min(startPos.getX(), endPos.getX()), Math.min(startPos.getY(), endPos.getY()), Math.min(startPos.getZ(), endPos.getZ()));
			BlockPos pos2 = new BlockPos(Math.max(startPos.getX(), endPos.getX()), Math.max(startPos.getY(), endPos.getY()), Math.max(startPos.getZ(), endPos.getZ()));

			for (int x = pos1.getX(); x <= pos2.getX(); x++) {
				for (int y = pos1.getY(); y <= pos2.getY(); y++) {
					for (int z = pos1.getZ(); z <= pos2.getZ(); z++) {
						if (x != 0 || y != 0 || z != 0) {
							BlockPos pos = basePos.add(x, y, z);

							world.setBlockState(pos, block.getDefaultState(), 3);
							TileEntityBuilding tileEntity = (TileEntityBuilding) world.getTileEntity(pos);
							tileEntity.basePos = basePos;
							tileEntity.isBase = false;
							tileEntity.isDoor = isDoor;
						}
					}
				}
			}
		}
	}

	public static void setAirBlocks(World world, BlockPos basePos, BlockPos startPos, BlockPos endPos, Rotation rotation) {
		if (!world.isRemote) {
			startPos = startPos.rotate(rotation);
			endPos = endPos.rotate(rotation);
			BlockPos pos1 = new BlockPos(Math.min(startPos.getX(), endPos.getX()), Math.min(startPos.getY(), endPos.getY()), Math.min(startPos.getZ(), endPos.getZ()));
			BlockPos pos2 = new BlockPos(Math.max(startPos.getX(), endPos.getX()), Math.max(startPos.getY(), endPos.getY()), Math.max(startPos.getZ(), endPos.getZ()));

			for (int x = pos1.getX(); x <= pos2.getX(); x++) {
				for (int y = pos1.getY(); y <= pos2.getY(); y++) {
					for (int z = pos1.getZ(); z <= pos2.getZ(); z++) {
						BlockPos pos = basePos.add(x, y, z);

						world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
					}
				}
			}
		}
	}

	public static boolean checkBlocks(World world, BlockPos basePos, BlockPos startPos, BlockPos endPos, Rotation rotation) {
		startPos = startPos.rotate(rotation);
		endPos = endPos.rotate(rotation);
		BlockPos pos1 = new BlockPos(Math.min(startPos.getX(), endPos.getX()), Math.min(startPos.getY(), endPos.getY()), Math.min(startPos.getZ(), endPos.getZ()));
		BlockPos pos2 = new BlockPos(Math.max(startPos.getX(), endPos.getX()), Math.max(startPos.getY(), endPos.getY()), Math.max(startPos.getZ(), endPos.getZ()));

		for (int x = pos1.getX(); x <= pos2.getX(); x++) {
			for (int y = pos1.getY(); y <= pos2.getY(); y++) {
				for (int z = pos1.getZ(); z <= pos2.getZ(); z++) {
					BlockPos pos = basePos.add(x, y, z);

					if (!world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static boolean checkDoorBlocks(World world, BlockPos basePos, BlockPos startPos, BlockPos endPos, Rotation rotation) {
		startPos = startPos.rotate(rotation);
		endPos = endPos.rotate(rotation);
		BlockPos pos1 = new BlockPos(Math.min(startPos.getX(), endPos.getX()), Math.min(startPos.getY(), endPos.getY()), Math.min(startPos.getZ(), endPos.getZ()));
		BlockPos pos2 = new BlockPos(Math.max(startPos.getX(), endPos.getX()), Math.max(startPos.getY(), endPos.getY()), Math.max(startPos.getZ(), endPos.getZ()));

		for (int x = pos1.getX(); x <= pos2.getX(); x++) {
			for (int y = pos1.getY(); y <= pos2.getY(); y++) {
				for (int z = pos1.getZ(); z <= pos2.getZ(); z++) {
					BlockPos pos = basePos.add(x, y, z);

					TileEntity tileEntity = world.getTileEntity(pos);
					if (!(tileEntity instanceof TileEntityBuilding) || !((TileEntityBuilding) tileEntity).isDoor) {
						return false;
					}
				}
			}
		}

		return true;
	}

}
