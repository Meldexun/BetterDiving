package meldexun.better_diving.block;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.tileentity.TileEntityCreepvine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCreepvine extends AbstractBlockCreepvine {

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		if (worldIn.getBlockState(pos).getBlock() instanceof AbstractBlockCreepvine && !(worldIn.getBlockState(pos.up()).getBlock() instanceof AbstractBlockCreepvine)) {
			TileEntityCreepvine tileEntity = (TileEntityCreepvine) worldIn.getTileEntity(pos);
			ModBlocks.CREEPVINE_TOP.setCreepvine(worldIn, pos, 3, tileEntity.getMaxHeight(), tileEntity.canGenerateSeeds());
		}
	}

	// prevents deletion of creepvine when updating from v1.4.04

	public static final PropertyInteger SEED = PropertyInteger.create("seed", 0, 3); // 0=top 1=full 2=seed 3=seeds

	public BlockCreepvine() {
		if (!(this instanceof BlockCreepvineSeed)) {
			this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, 0).withProperty(BlockCreepvine.SEED, 0));
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockLiquid.LEVEL, BlockCreepvine.SEED);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getPropertyKeys().contains(BlockCreepvine.SEED) ? state.getValue(BlockCreepvine.SEED) : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (meta == 0 && ModBlocks.CREEPVINE_TOP != null) {
			return ModBlocks.CREEPVINE_TOP.getDefaultState();
		} else if (meta == 1 && ModBlocks.CREEPVINE != null) {
			return ModBlocks.CREEPVINE.getDefaultState();
		} else if ((meta == 2 && ModBlocks.CREEPVINE_SEED != null) || (meta == 3 && ModBlocks.CREEPVINE_SEED != null)) {
			return ModBlocks.CREEPVINE_SEED.getDefaultState();
		}
		return this.getDefaultState();
	}

}
