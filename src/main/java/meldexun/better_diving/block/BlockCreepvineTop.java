package meldexun.better_diving.block;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.tileentity.TileEntityCreepvine;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCreepvineTop extends AbstractBlockCreepvine {

	public static final AxisAlignedBB HALF_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return HALF_BLOCK_AABB;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		if (worldIn.getBlockState(pos).getBlock() instanceof AbstractBlockCreepvine && worldIn.getBlockState(pos.up()).getBlock() instanceof AbstractBlockCreepvine) {
			TileEntityCreepvine tileEntity = (TileEntityCreepvine) worldIn.getTileEntity(pos);
			ModBlocks.CREEPVINE.setCreepvine(worldIn, pos, 3, tileEntity.maxHeight, tileEntity.generateSeeds);
		}
	}

}
