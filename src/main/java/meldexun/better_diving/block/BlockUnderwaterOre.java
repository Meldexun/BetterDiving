package meldexun.better_diving.block;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockUnderwaterOre extends Block implements IWaterLoggable {

	public static final IntegerProperty ROTATION = IntegerProperty.create("rotated", 0, 3);
	private static final Map<Direction, VoxelShape> SHAPES;
	static {
		Map<Direction, VoxelShape> m = new EnumMap<>(Direction.class);
		m.put(Direction.UP, Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D));
		m.put(Direction.DOWN, Block.makeCuboidShape(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D));
		m.put(Direction.NORTH, Block.makeCuboidShape(2.0D, 2.0D, 8.0D, 14.0D, 14.0D, 16.0D));
		m.put(Direction.SOUTH, Block.makeCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 8.0D));
		m.put(Direction.EAST, Block.makeCuboidShape(0.0D, 2.0D, 2.0D, 8.0D, 14.0D, 14.0D));
		m.put(Direction.WEST, Block.makeCuboidShape(8.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
		SHAPES = Collections.unmodifiableMap(m);
	}

	public BlockUnderwaterOre() {
		super(AbstractBlock.Properties.create(Material.SAND));
		this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.FACING, Direction.UP).with(ROTATION, 0).with(BlockStateProperties.WATERLOGGED, true));
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.get(BlockStateProperties.FACING));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING, ROTATION, BlockStateProperties.WATERLOGGED);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction d = state.get(BlockStateProperties.FACING);
		BlockPos p = pos.offset(d.getOpposite());
		return worldIn.getBlockState(p).isSolidSide(worldIn, p, d);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = this.getDefaultState();
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		for (Direction d : context.getNearestLookingDirections()) {
			state = state.with(BlockStateProperties.FACING, d.getOpposite());
			if (this.isValidPosition(state, world, pos)) {
				return state.with(ROTATION, world.rand.nextInt(4));
			}
		}
		return null;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (facing.getOpposite() == stateIn.get(BlockStateProperties.FACING) && !stateIn.isValidPosition(worldIn, currentPos)) {
			return Blocks.AIR.getDefaultState();
		}
		if (stateIn.get(BlockStateProperties.WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return stateIn;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(BlockStateProperties.FACING, rot.rotate(state.get(BlockStateProperties.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(BlockStateProperties.FACING, mirrorIn.mirror(state.get(BlockStateProperties.FACING)));
	}

}
