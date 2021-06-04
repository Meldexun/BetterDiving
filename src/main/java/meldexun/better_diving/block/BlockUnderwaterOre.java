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
		m.put(Direction.UP, Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D));
		m.put(Direction.DOWN, Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D));
		m.put(Direction.NORTH, Block.box(2.0D, 2.0D, 8.0D, 14.0D, 14.0D, 16.0D));
		m.put(Direction.SOUTH, Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 8.0D));
		m.put(Direction.EAST, Block.box(0.0D, 2.0D, 2.0D, 8.0D, 14.0D, 14.0D));
		m.put(Direction.WEST, Block.box(8.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
		SHAPES = Collections.unmodifiableMap(m);
	}

	public BlockUnderwaterOre() {
		super(AbstractBlock.Properties.of(Material.SAND));
		this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.UP).setValue(ROTATION, 0).setValue(BlockStateProperties.WATERLOGGED, true));
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.getValue(BlockStateProperties.FACING));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING, ROTATION, BlockStateProperties.WATERLOGGED);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction d = state.getValue(BlockStateProperties.FACING);
		BlockPos p = pos.relative(d.getOpposite());
		return worldIn.getBlockState(p).isFaceSturdy(worldIn, p, d);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = this.defaultBlockState();
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		for (Direction d : context.getNearestLookingDirections()) {
			state = state.setValue(BlockStateProperties.FACING, d.getOpposite());
			if (this.canSurvive(state, world, pos)) {
				return state.setValue(ROTATION, world.random.nextInt(4));
			}
		}
		return null;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (facing.getOpposite() == stateIn.getValue(BlockStateProperties.FACING) && !stateIn.canSurvive(worldIn, currentPos)) {
			return Blocks.AIR.defaultBlockState();
		}
		if (stateIn.getValue(BlockStateProperties.WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return stateIn;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(BlockStateProperties.FACING, rot.rotate(state.getValue(BlockStateProperties.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.setValue(BlockStateProperties.FACING, mirrorIn.mirror(state.getValue(BlockStateProperties.FACING)));
	}

}
