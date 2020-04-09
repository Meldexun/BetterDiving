package meldexun.better_diving.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSeabaseLadder extends BlockHorizontal {

	protected static final AxisAlignedBB LADDER_EAST_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.125D, 0.9375D, 1.0D, 0.875D);
	protected static final AxisAlignedBB LADDER_WEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.125D, 0.1875D, 1.0D, 0.875D);
	protected static final AxisAlignedBB LADDER_SOUTH_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.8125D, 0.875D, 1.0D, 0.9375D);
	protected static final AxisAlignedBB LADDER_NORTH_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0625D, 0.875D, 1.0D, 0.1875D);

	/**
	 * 0=alone, 1=bottom, 2=middle, 3=top
	 */
	protected static final PropertyInteger PART = PropertyInteger.create("part", 0, 3);

	public BlockSeabaseLadder() {
		super(Material.IRON);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(BlockSeabaseLadder.PART, 0));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(BlockHorizontal.FACING)) {
		case NORTH:
			return BlockSeabaseLadder.LADDER_NORTH_AABB;
		case SOUTH:
			return BlockSeabaseLadder.LADDER_SOUTH_AABB;
		case WEST:
			return BlockSeabaseLadder.LADDER_WEST_AABB;
		case EAST:
			return BlockSeabaseLadder.LADDER_EAST_AABB;
		default:
			return BlockSeabaseLadder.LADDER_NORTH_AABB;
		}
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
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(placer.rotationYaw));
		Block blockDown = worldIn.getBlockState(pos.down()).getBlock();
		Block blockUp = worldIn.getBlockState(pos.up()).getBlock();
		if (blockDown != this && blockUp != this) {
			return state.withProperty(BlockSeabaseLadder.PART, 0);
		} else if (blockDown != this) {
			return state.withProperty(BlockSeabaseLadder.PART, 1);
		} else if (blockUp == this) {
			return state.withProperty(BlockSeabaseLadder.PART, 2);
		}
		return state.withProperty(BlockSeabaseLadder.PART, 3);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		int part = state.getValue(BlockSeabaseLadder.PART);
		Block blockDown = worldIn.getBlockState(pos.down()).getBlock();
		Block blockUp = worldIn.getBlockState(pos.up()).getBlock();
		if (part != 0 && blockDown != this && blockUp != this) {
			worldIn.setBlockState(pos, state.withProperty(BlockSeabaseLadder.PART, 0));
		} else if (part != 1 && blockDown != this && blockUp == this) {
			worldIn.setBlockState(pos, state.withProperty(BlockSeabaseLadder.PART, 1));
		} else if (part != 2 && blockDown == this && blockUp == this) {
			worldIn.setBlockState(pos, state.withProperty(BlockSeabaseLadder.PART, 2));
		} else if (part != 3 && blockDown == this && blockUp != this) {
			worldIn.setBlockState(pos, state.withProperty(BlockSeabaseLadder.PART, 3));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(BlockSeabaseLadder.PART, meta / 4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex() + 4 * state.getValue(BlockSeabaseLadder.PART);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rotation) {
		return state.withProperty(BlockHorizontal.FACING, rotation.rotate(state.getValue(BlockHorizontal.FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror) {
		return state.withRotation(mirror.toRotation(state.getValue(BlockHorizontal.FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING, BlockSeabaseLadder.PART);
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
		return true;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

}
