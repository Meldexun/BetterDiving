package meldexun.better_diving.structure;

import meldexun.better_diving.init.ModBlocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BasicCompartment extends SeabaseModule {

	@Override
	public void generate(World world, BlockPos pos, Rotation rotation) {
		if (!world.isRemote && this.checkPos(world, pos, rotation)) {
			setBase(world, pos, ModBlocks.STRUCTURE_BLOCK);

			setBlocks(world, pos, new BlockPos(-2, 0, 0), new BlockPos(3, 0, 1), rotation, ModBlocks.STRUCTURE_BLOCK, false);
			setBlocks(world, pos, new BlockPos(-2, 3, 0), new BlockPos(3, 3, 1), rotation, ModBlocks.STRUCTURE_BLOCK, false);
			setBlocks(world, pos, new BlockPos(-2, 1, -1), new BlockPos(3, 2, -1), rotation, ModBlocks.STRUCTURE_BLOCK, false);
			setBlocks(world, pos, new BlockPos(-2, 1, 2), new BlockPos(3, 2, 2), rotation, ModBlocks.STRUCTURE_BLOCK, false);

			setAirBlocks(world, pos, new BlockPos(-1, 1, 0), new BlockPos(2, 2, 1), rotation);

			if (checkDoorBlocks(world, pos, new BlockPos(-3, 1, 0), new BlockPos(-3, 2, 1), rotation)) {
				setAirBlocks(world, pos, new BlockPos(-3, 1, 0), new BlockPos(-2, 2, 1), rotation);
			} else {
				setBlocks(world, pos, new BlockPos(-2, 1, 0), new BlockPos(-2, 2, 1), rotation, ModBlocks.STRUCTURE_BLOCK, true);
			}

			if (checkDoorBlocks(world, pos, new BlockPos(4, 1, 0), new BlockPos(4, 2, 1), rotation)) {
				setAirBlocks(world, pos, new BlockPos(3, 1, 0), new BlockPos(4, 2, 1), rotation);
			} else {
				setBlocks(world, pos, new BlockPos(3, 1, 0), new BlockPos(3, 2, 1), rotation, ModBlocks.STRUCTURE_BLOCK, true);
			}
		}
	}

	@Override
	public boolean checkPos(World world, BlockPos pos, Rotation rotation) {
		if (!checkBlocks(world, pos, new BlockPos(-2, 0, 0), new BlockPos(3, 3, 1), rotation)) {
			return false;
		}
		if (!checkBlocks(world, pos, new BlockPos(-2, 1, -1), new BlockPos(3, 2, -1), rotation)) {
			return false;
		}
		if (!checkBlocks(world, pos, new BlockPos(-2, 1, 2), new BlockPos(3, 2, 2), rotation)) {
			return false;
		}
		return true;
	}

}
