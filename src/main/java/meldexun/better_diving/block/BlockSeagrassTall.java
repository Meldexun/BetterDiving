package meldexun.better_diving.block;

import java.util.Random;

import meldexun.better_diving.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSeagrassTall extends BlockUnderwaterBlock {

	@Override
	public boolean checkTop(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == ModBlocks.SEAGRASS_TALL_BOTTOM) {
			return world.getBlockState(pos.up()).getBlock() == ModBlocks.SEAGRASS_TALL_TOP;
		}
		return super.checkTop(world, pos);
	}

	@Override
	public boolean checkBottom(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == ModBlocks.SEAGRASS_TALL_TOP) {
			return world.getBlockState(pos.down()).getBlock() == ModBlocks.SEAGRASS_TALL_BOTTOM;
		}
		return super.checkBottom(world, pos);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.SEAGRASS_TALL_BOTTOM);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getMaterial() != Material.WATER) {
			return false;
		}
		if (!this.checkBottom(worldIn, pos)) {
			return false;
		}
		for (int i = 0; i < 4; i++) {
			if (!this.isFullCubeOrWater(worldIn.getBlockState(pos.offset(EnumFacing.getHorizontal(i))))) {
				return false;
			}
		}
		pos = pos.up();
		if (worldIn.getBlockState(pos).getMaterial() != Material.WATER) {
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

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (worldIn.getBlockState(pos).getBlock() == ModBlocks.SEAGRASS_TALL_TOP) {
			worldIn.setBlockState(pos, ModBlocks.SEAGRASS_TALL_BOTTOM.getDefaultState(), 3);
		}
		worldIn.setBlockState(pos.up(), ModBlocks.SEAGRASS_TALL_TOP.getDefaultState(), 3);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canBlockStay(worldIn, pos)) {
			worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
		}
	}

}
