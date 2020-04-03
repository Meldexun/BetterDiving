package meldexun.better_diving.block;

import java.util.Random;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.tileentity.TileEntityCreepvine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractBlockCreepvine extends BlockUnderwaterBlock {

	public static final int MAX_HEIGHT = 14;

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCreepvine(MAX_HEIGHT, false);
	}

	public boolean setCreepvine(World world, BlockPos pos, int flags, int maxHeight, boolean generateSeeds) {
		if (world.setBlockState(pos, this.getDefaultState(), flags)) {
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof TileEntityCreepvine) {
				((TileEntityCreepvine) tileEntity).maxHeight = maxHeight;
				((TileEntityCreepvine) tileEntity).generateSeeds = generateSeeds;
			} else {
				System.err.println("Failed to properly place creepvine block at " + pos);
			}
			return true;
		}

		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.CREEPVINE);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (worldIn.getTileEntity(pos.down()) instanceof TileEntityCreepvine) {
			TileEntityCreepvine tileEntity1 = (TileEntityCreepvine) worldIn.getTileEntity(pos.down());
			TileEntityCreepvine tileEntity2 = (TileEntityCreepvine) worldIn.getTileEntity(pos);
			tileEntity2.maxHeight = tileEntity1.maxHeight;
			tileEntity2.generateSeeds = tileEntity1.generateSeeds;
		}
	}

	@Override
	public boolean checkBottom(World world, BlockPos pos) {
		if (super.checkBottom(world, pos)) {
			return true;
		}
		return world.getBlockState(pos.down()).getBlock() instanceof AbstractBlockCreepvine;
	}

}
