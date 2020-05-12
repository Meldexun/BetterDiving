package meldexun.better_diving.block;

import javax.annotation.Nullable;

import meldexun.better_diving.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockUnderwaterBlock extends Block {

	public BlockUnderwaterBlock() {
		super(Material.WATER);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, 0));
		this.setSoundType(SoundType.SAND);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockLiquid.LEVEL);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getMaterial() != Material.WATER) {
			return false;
		}
		if (!this.checkBottom(worldIn, pos)) {
			return false;
		}
		if (!this.checkTop(worldIn, pos)) {
			return false;
		}
		for (int i = 0; i < 4; i++) {
			if (!this.isFullCubeOrWater(worldIn.getBlockState(pos.offset(EnumFacing.getHorizontal(i))))) {
				return false;
			}
		}
		return true;
	}

	public boolean canBlockStay(World world, BlockPos pos) {
		if (!this.checkBottom(world, pos)) {
			return false;
		}
		if (!this.checkTop(world, pos)) {
			return false;
		}
		for (int i = 0; i < 4; i++) {
			if (!this.checkSide(world, pos, EnumFacing.getHorizontal(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean checkBottom(World world, BlockPos pos) {
		BlockPos position = pos.down();
		Material material = world.getBlockState(position).getMaterial();
		return material == Material.CLAY || material == Material.GROUND || material == Material.ROCK || material == Material.SAND;
	}

	public boolean checkTop(World world, BlockPos pos) {
		BlockPos position = pos.up();
		IBlockState state = world.getBlockState(position);
		if (state.getMaterial() == Material.WATER) {
			return true;
		}
		if (state.getBlock() == Blocks.AIR) {
			if (BlockHelper.isWaterBlock(world.getBlockState(position.up()))) {
				return true;
			}
			for (int i = 0; i < 4; i++) {
				if (BlockHelper.isWaterBlock(world.getBlockState(position.offset(EnumFacing.getHorizontal(i))), 6)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkSide(World world, BlockPos pos, EnumFacing facing) {
		BlockPos position = pos.offset(facing);
		IBlockState state = world.getBlockState(position);
		if (this.isFullCubeOrWater(state)) {
			return true;
		}
		if (state.getBlock() == Blocks.AIR) {
			if (BlockHelper.isWaterBlock(world.getBlockState(position.up()))) {
				return true;
			}
			if (BlockHelper.isWaterBlock(world.getBlockState(position.offset(facing.rotateYCCW())), 6)) {
				return true;
			}
			if (BlockHelper.isWaterBlock(world.getBlockState(position.offset(facing)), 6)) {
				return true;
			}
			if (BlockHelper.isWaterBlock(world.getBlockState(position.offset(facing.rotateY())), 6)) {
				return true;
			}
		}
		return false;
	}

	public boolean isFullCubeOrWater(IBlockState state) {
		return state.getMaterial() == Material.WATER || state.isFullCube();
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canBlockStay(worldIn, pos)) {
			worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
			this.dropBlockAsItem(worldIn, pos, state, 0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return Block.NULL_AABB;
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
	}

}
