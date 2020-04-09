package meldexun.better_diving.structure.modules;

import meldexun.better_diving.init.ModBlocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultipurposeRoom extends SeabaseModule {

	public MultipurposeRoom(World world) {
		super(world);
	}

	public MultipurposeRoom(World world, int x, int y, int z, Rotation rot) {
		super(world, x, y, z, rot);
		this.addWall(ModBlocks.STRUCTURE_BLOCK, new BlockPos(-2, 0, 0), new BlockPos(3, 0, 1), EnumFacing.SOUTH);
		this.addWall(ModBlocks.STRUCTURE_BLOCK, new BlockPos(-2, 3, 0), new BlockPos(3, 3, 1), EnumFacing.SOUTH);
		this.addWall(ModBlocks.STRUCTURE_BLOCK, new BlockPos(-2, 1, -1), new BlockPos(3, 2, -1), EnumFacing.SOUTH);
		this.addWall(ModBlocks.STRUCTURE_BLOCK, new BlockPos(-2, 1, 2), new BlockPos(3, 2, 2), EnumFacing.SOUTH);

		this.addDoor(ModBlocks.STRUCTURE_BLOCK, new BlockPos(-2, 1, 0), new BlockPos(-2, 2, 1), EnumFacing.WEST);
		this.addDoor(ModBlocks.STRUCTURE_BLOCK, new BlockPos(3, 1, 0), new BlockPos(3, 2, 1), EnumFacing.EAST);
	}

	@Override
	public void setAirBlocks() {
		this.setAirBlocks(new BlockPos(-4, 1, 6), new BlockPos(5, 4, 6));
		this.setAirBlocks(new BlockPos(6, 1, -4), new BlockPos(6, 4, 5));
		this.setAirBlocks(new BlockPos(-4, 1, -5), new BlockPos(5, 4, -5));
		this.setAirBlocks(new BlockPos(-5, 1, -4), new BlockPos(-5, 4, 5));
		this.setAirBlocks(new BlockPos(-4, 1, -4), new BlockPos(5, 4, 5));
	}

	@Override
	public boolean canSetAirBlocks() {
		return true;
	}

}
