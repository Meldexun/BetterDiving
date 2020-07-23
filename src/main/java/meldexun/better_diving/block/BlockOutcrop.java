package meldexun.better_diving.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOutcrop extends BlockUnderwaterBlock {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final AxisAlignedBB OUTCROP_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.7D, 0.9D);

	private int drop;
	private Map<Item, Integer> dropList = new HashMap<>();

	public BlockOutcrop() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, 0).withProperty(BlockOutcrop.FACING, EnumFacing.SOUTH));
		this.setHardness(0.4F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockLiquid.LEVEL, BlockOutcrop.FACING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockOutcrop.FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockOutcrop.FACING, EnumFacing.byHorizontalIndex(meta));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getStateFromMeta(Block.RANDOM.nextInt(4));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BlockOutcrop.OUTCROP_AABB;
	}

	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		double d = rand.nextDouble() * this.drop;
		for (Entry<Item, Integer> entry : this.dropList.entrySet()) {
			d -= entry.getValue();
			if (d <= 0) {
				return entry.getKey();
			}
		}
		return null;
	}

	public void addDrop(Item item, int weight) {
		this.drop += weight;
		this.dropList.put(item, weight);
	}

}
